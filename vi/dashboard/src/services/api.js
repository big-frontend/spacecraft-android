import { request } from '@umijs/max';

export async function getApkStatement(biz,version) {
  let url = `/api/apkstatement/${biz}`
  if (version) {
    url += "/" + version
  }
  return request(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    }
  });
}