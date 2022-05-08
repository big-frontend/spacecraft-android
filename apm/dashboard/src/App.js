import React from 'react';
import './App.css';
import 'antd/dist/antd.css';
import {Layout, Menu, Breadcrumb} from 'antd';
import {
    DesktopOutlined, PieChartOutlined, FileOutlined, TeamOutlined, UserOutlined,
} from '@ant-design/icons';
import logo from './logo.svg';
import {Link, Route, Switch} from "react-router-dom";

export default function App(props) {
    return (// <div className="App">
        //   <header className="App-header">
        //     <img src={logo} className="App-logo" alt="logo" />
        //     <p>
        //       Edit <code>src/App.js</code> and save to reload.
        //     </p>
        //     <a
        //       className="App-link"
        //       href="https://reactjs.org"
        //       target="_blank"
        //       rel="noopener noreferrer"
        //     >
        //       Learn React
        //     </a>
        //   </header>
        // </div>
        <Layout style={{
            minHeight: '100vh',
        }}>
            <Layout.Sider
                collapsible
                // breakpoint="lg"
                // collapsedWidth="0"
                onBreakpoint={broken => {
                    console.log(broken);
                }}
                onCollapse={(collapsed, type) => {
                    console.log(collapsed, type);
                }}>
                <img src={logo} className="App-logo" alt="logo"/>
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline"
                    // items={[{key: "1", icon: React.createElement(DesktopOutlined), label: '构建速度专项'}, {
                    //     key: "2", icon: React.createElement(UserOutlined), label: 'd'
                    // }, {key: "3", icon: React.createElement(UserOutlined), label: 'c'}, {
                    //     key: "4",
                    //     icon: React.createElement(UserOutlined),
                    //     children: [{
                    //         key: "41",
                    //         icon: React.createElement(UserOutlined),
                    //         label: 'bugly崩溃专项'
                    //     }, {key: "42", icon: React.createElement(UserOutlined), label: 'eap崩溃专项'},],
                    //     label: '崩溃专项'
                    // },]}
                >
                    <Menu.Item key='1' icon={React.createElement(DesktopOutlined)}>
                        <Link to='/oapm'>oapm专项</Link>
                    </Menu.Item>
                    <Menu.SubMenu key='2' icon={React.createElement(UserOutlined)} title='崩溃专项'>
                        <Menu.Item key='21' icon={React.createElement(DesktopOutlined)}>
                            <Link to='/buglycrash'>bugly崩溃专项</Link>
                        </Menu.Item>
                        <Menu.Item key='22' icon={React.createElement(DesktopOutlined)}>
                            <Link to='/eapcrash'>eap崩溃专项</Link>
                        </Menu.Item>
                    </Menu.SubMenu>

                    <Menu.Item key='3' icon={React.createElement(DesktopOutlined)}>
                        <Link to='/build'>构建速度专项</Link>
                    </Menu.Item>
                    <Menu.Item key='4' icon={React.createElement(DesktopOutlined)}>
                        <Link to='/net'>网络专项</Link>
                    </Menu.Item>
                    <Menu.Item key='5' icon={React.createElement(DesktopOutlined)}>
                        <Link to='/download'>资源下载专项</Link>
                    </Menu.Item>
                </Menu>
            </Layout.Sider>
            <Layout.Content>
                <Switch>
                    <Route path="/build" key="/build"
                           render={props => <Breadcrumb
                               style={{
                                   margin: '16px 0',
                               }}
                               {...props}
                           >
                               <Breadcrumb.Item>android build</Breadcrumb.Item>
                               <Breadcrumb.Item>android build</Breadcrumb.Item>
                           </Breadcrumb>}
                    />
                    <Route path="/buglycrash"
                           key="/buglycrash"
                           render={props => <iframe
                               src='https://bugly.qq.com/v2/crash-reporting/dashboard/968f48b3e5?pid=1'
                               width="100%"
                               height="100%"
                               title="bugly"
                           />}>

                    </Route>
                    <Route path="/eapcrash" key="/eapcrash">
                        <iframe src='https://eap.oppoer.me/#/app/availableMonitor/dashboard'
                                width="100%"
                                height="100%"
                                title="eap"
                        />
                    </Route>
                    <Route path="/oapm" key="/oapm">
                        <iframe src='http://oapm.wanyol.com/overview'
                                width="100%"
                                height="100%"
                                title="oapm"
                        />
                    </Route>
                </Switch>
            </Layout.Content></Layout>);
}
