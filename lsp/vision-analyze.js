#!/usr/bin/env node
'use strict';
// Vision helper for logisim-ai: reuses the same Zhipu vision model that
// opencode's zai-vision-mcp is configured with (glm-4.1v-thinking-flash),
// calling the OpenAI-compatible endpoint directly. Useful when the MCP tool
// is not available in the current harness.
//
// Usage:
//   node lsp\vision-analyze.js <image-path> [question]
//   node lsp\vision-analyze.js --url <image-url> [question]
//
// Env: reads Z_AI_API_KEY from the USER environment (setx) if not set in the
//      current process, then falls back to DASHSCOPE_API_KEY (Qwen) when
//      Z_AI_API_KEY is absent.

const fs = require('fs');
const path = require('path');
const https = require('https');
const http = require('http');

function userEnv(name) {
  // PowerShell-style user-level env lookup on Windows
  if (process.platform === 'win32') {
    try {
      const { execSync } = require('child_process');
      const out = execSync(
        `powershell -NoProfile -Command "[Environment]::GetEnvironmentVariable('${name}','User')"`,
        { encoding: 'utf8', windowsHide: true }).trim();
      return out || undefined;
    } catch { return undefined; }
  }
  return undefined;
}

function usage() {
  console.error('Usage: node lsp\\vision-analyze.js <image-path> [question]');
  console.error('       node lsp\\vision-analyze.js --url <image-url> [question]');
  console.error('Uses Z_AI_API_KEY (Zhipu glm-4.1v-thinking-flash) or DASHSCOPE_API_KEY (Qwen).');
  process.exit(2);
}

function parseArgs() {
  const argv = process.argv.slice(2);
  let imageSource = '', prompt = '', isUrl = false;
  for (let i = 0; i < argv.length; i++) {
    const a = argv[i];
    if (a === '--url' && argv[i + 1]) { isUrl = true; imageSource = argv[++i]; }
    else if (!imageSource && !a.startsWith('--')) { imageSource = a; }
    else if (imageSource && !a.startsWith('--')) { prompt = prompt ? `${prompt} ${a}` : a; }
  }
  return { imageSource, prompt, isUrl };
}

function postJson(url, headers, body, timeoutMs) {
  return new Promise((resolve, reject) => {
    const lib = url.startsWith('https') ? https : http;
    const req = lib.request(url, { method: 'POST', headers, timeout: timeoutMs }, (res) => {
      let data = '';
      res.on('data', (c) => (data += c));
      res.on('end', () => {
        try { resolve({ status: res.statusCode, json: JSON.parse(data) }); }
        catch { reject(new Error(`bad JSON (HTTP ${res.statusCode}): ${data.slice(0, 300)}`)); }
      });
    });
    req.on('error', reject);
    req.on('timeout', () => { req.destroy(new Error('timeout')); });
    req.write(JSON.stringify(body));
    req.end();
  });
}

async function main() {
  const { imageSource, prompt, isUrl } = parseArgs();
  if (!imageSource) usage();

  let zKey = process.env.Z_AI_API_KEY || userEnv('Z_AI_API_KEY');
  let dashKey = process.env.DASHSCOPE_API_KEY || userEnv('DASHSCOPE_API_KEY');

  let imageUrl = imageSource;
  if (!isUrl) {
    const p = path.resolve(imageSource);
    if (!fs.existsSync(p)) { console.error(`image not found: ${p}`); process.exit(2); }
    const b64 = fs.readFileSync(p).toString('base64');
    const ext = path.extname(p).toLowerCase().replace('.', '');
    const mime = { png: 'image/png', jpg: 'image/jpeg', jpeg: 'image/jpeg', webp: 'image/webp', gif: 'image/gif' }[ext] || 'image/png';
    imageUrl = `data:${mime};base64,${b64}`;
  }

  const question = prompt || '描述这张图片的内容（中文）。';

  if (zKey) {
    const body = {
      model: process.env.Z_AI_VISION_MODEL || 'glm-4.1v-thinking-flash',
      messages: [{ role: 'user', content: [
        { type: 'text', text: question },
        { type: 'image_url', image_url: { url: imageUrl } },
      ]}],
      max_tokens: 512,
    };
    try {
      const { status, json } = await postJson(
        'https://open.bigmodel.cn/api/paas/v4/chat/completions',
        { Authorization: `Bearer ${zKey}`, 'Content-Type': 'application/json' },
        body, 90000);
      if (status !== 200) {
        console.error(`Zhipu HTTP ${status}: ${JSON.stringify(json).slice(0, 400)}`);
      } else {
        const msg = json.choices && json.choices[0] && json.choices[0].message;
        if (msg) {
          // Strip thinking block from content when present
          const content = (msg.content || '').replace(/<think>[\s\S]*?<\/think>/g, '').trim();
          console.log(content || '(empty content)');
          if (msg.reasoning_content) console.error(`[thinking] ${String(msg.reasoning_content).slice(0, 300)}`);
          return;
        }
      }
    } catch (e) {
      console.error(`Zhipu call failed: ${e.message}`);
    }
  }

  if (dashKey) {
    const body = {
      model: process.env.VISION_MODEL || 'qwen-vl-max',
      messages: [{ role: 'user', content: [
        { type: 'text', text: question },
        { type: 'image_url', image_url: { url: imageUrl } },
      ]}],
    };
    try {
      const { status, json } = await postJson(
        'https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions',
        { Authorization: `Bearer ${dashKey}`, 'Content-Type': 'application/json' },
        body, 90000);
      if (status !== 200) {
        console.error(`DashScope HTTP ${status}: ${JSON.stringify(json).slice(0, 400)}`);
        process.exit(1);
      }
      const content = json.choices[0].message.content;
      console.log(content);
      return;
    } catch (e) {
      console.error(`DashScope call failed: ${e.message}`);
    }
  }

  console.error('No usable vision key found (need Z_AI_API_KEY or DASHSCOPE_API_KEY).');
  process.exit(1);
}

main().catch((e) => { console.error(e.message); process.exit(1); });
