import { PageContainer } from '@ant-design/pro-components';
import React, { useRef, useState, useEffect } from 'react';

const BuglyCrashPage = () => {
  const url = 'https://bugly.qq.com/v2/crash-reporting/dashboard/968f48b3e5?pid=1'
  useEffect(() => {
    window.open(url, '_blank', 'noopener,noreferrer');
  }, [])
  // return <iframe src={{url}} width="100%" height="100%" title="bugly"/>
  return <a href={{ url }} target="_blank" rel="noopener noreferrer">bugly</a>
}
export default BuglyCrashPage;