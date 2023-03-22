import { Settings as LayoutSettings } from '@ant-design/pro-components';

/**
 * @name
 */
const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  colorPrimary: '#1890ff',
  // layout: 'mix',
  layout:'side',
  contentWidth: 'Fluid',
  fixedHeader: true,
  fixSiderbar: true,
  pwa: false,
  logo: 'logo.png',
  // splitMenus: false,
  // siderMenuType: "sub",
  title: 'apm',
  colorWeak: true,
  iconfontUrl: '',
  menu:{
    hideMenuWhenCollapsed:false,
  }
};

export default Settings;
