import React, { Component, useState, useEffect, useRef } from 'react';
import { Line } from '@ant-design/charts';
import { Pie, G2 } from '@ant-design/plots';
import { PageContainer, ProCard } from '@ant-design/pro-components';
import { Button, Collapse } from 'antd';
import { getApkStatement } from '@/services/api'
import { Divider, Radio, Typography, message, Col, Row, Spin } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import P from './components/P';
const { Paragraph, Text } = Typography;
const { Panel } = Collapse;
const G = G2.getEngine('canvas');
const tabs = [
    {
        tab: '整包',
        key: 'apk',
        closable: false,
        url: 'c'
    },
    // {
    //     tab: '基础组件',
    //     key: 'fwk',
    //     closable: false,
    // },
    // {
    //     tab: '快应用组件',
    //     key: 'quickapp',
    //     closable: false,
    // },
    // {
    //     tab: '快游戏组件',
    //     key: 'quickgame',
    //     closable: false,
    // },
    // {
    //     tab: 'gemini(联盟chimera)组件',
    //     key: 'gemini',
    // },
    // {
    //     tab: '百度小程序组件',
    //     key: 'baidusmartapp',
    // },
]
function ApkAnalyzer() {
    const [curVersion, setCurVersion] = useState(0)
    const [loading, setLoading] = useState(true)
    const [versions, setVersions] = useState([])
    const [sizePie1, setSizePie1] = useState([])
    const [sizePie2, setSizePie2] = useState()
    const [pieType, setPieType] = useState('.dex')
    const [task9, setTask9] = useState()
    const [task13, setTask13] = useState()
    const [task12, setTask12] = useState()
    const [task10, setTask10] = useState()
    const [task8, setTask8] = useState()
    const [task6, setTask6] = useState()
    const [task14, setTask14] = useState()
    const [task3, setTask3] = useState()
    const [task7, setTask7] = useState()
    const [task11, setTask11] = useState()
    const [biz, setBiz] = useState('apk')
    const refreshTheVersion = async (b, v) => {
        setLoading(true)
        const resp = await getApkStatement(b, v)
        const { version_name, version_code } = resp.data[0]
        console.log(version_code, version_name, resp)
        setCurVersion(version_code);
        for (let index = 0; index < resp.data[0].tasks.length; index++) {
            const element = resp.data[0].tasks[index];
            if (!element) continue;
            if (element.task_type == 1) {
                let dexAndSoAndResAndAssets = []
                let soTotalSize = 0
                let soList = []
                let resTotalSize = 0
                let resList = []
                let assetsTotalSize = 0
                let assetsList = []
                let othersTotalSize = 0
                let othersList = []
                for (const e of element.entries) {
                    if (e.suffix == '.dex' || e.suffix == '.arsc') {
                        dexAndSoAndResAndAssets.push(e)
                    } else {
                        for (const f of e.files) {
                            if (f.entry_name.startsWith('res') || f.entry_name.startsWith('drawable') || f.entry_name.startsWith("layout")
                            || f.entry_name.startsWith("values")
                            || f.entry_name.startsWith("main/res")
                            || f.entry_name.startsWith("anim")
                            || f.entry_name.startsWith("xml")
                            ) {
                                resList.push(f)
                                resTotalSize += f.entry_size
                            } else if (f.entry_name.startsWith('assets')) {
                                assetsList.push(f)
                                assetsTotalSize += f.entry_size
                            } else if (f.entry_name.endsWith('.so')) {
                                soList.push(f)
                                soTotalSize += f.entry_size
                            } else {
                                othersList.push(f)
                                othersTotalSize += f.entry_size
                            }
                        }
                    }
                }
                dexAndSoAndResAndAssets.push({
                    "suffix": "so",
                    "total_size": soTotalSize,
                    "files": soList
                  })
                dexAndSoAndResAndAssets.push({
                    "suffix": "res",
                    "total_size": resTotalSize,
                    "files": resList
                  })
                  dexAndSoAndResAndAssets.push({
                    "suffix": "assets",
                    "total_size": assetsTotalSize,
                    "files": assetsList
                  })
                  dexAndSoAndResAndAssets.push({
                    "suffix": "others",
                    "total_size": othersTotalSize,
                    "files": othersList
                  })
                let l = []
                let ll = {}
                dexAndSoAndResAndAssets.map((e) => {
                    l.push({ type: e.suffix, value: e.total_size })
                    let lll = []
                    e.files.map((f) => {
                        lll.push({ type: f.entry_name, value: f.entry_size })
                    })
                    ll[e.suffix] = lll
                })
                setSizePie1(l)
                setSizePie2(ll)
            } else if (element.task_type == 5) {
            } else if (element.task_type == 6) {
                setTask6(element)
            } else if (element.task_type == 8) {
                setTask8(element)
            } else if (element.task_type == 10) {
                setTask10(element)
            } else if (element.task_type == 12) {
                setTask12(element)
            } else if (element.task_type == 13) {
                setTask13(element)
            } else if (element.task_type == 14) {
                setTask14(element)
            } else if (element.task_type == 3) {
                setTask3(element)
            } else if (element.task_type == 4) {
            } else if (element.task_type == 9) {
                setTask9(element)
            } else if (element.task_type == 7) {
                setTask7(element)
            } else if (element.task_type == 11) {
                setTask11(element)
            }
        }

        setLoading(false)
    }
    const clearPage = () => {
        setPieType('.dex')
        setVersions([])
        clearTheVersion();
    }
    const clearTheVersion = () => {
        setSizePie2()
        setSizePie1([])
        setTask9({});
        setTask13({})
        setTask12({})
        setTask10({})
        setTask8({})
        setTask6({})
        setCurVersion(0)
    }
    const refreshPage = async (b) => {
        const resp = await getApkStatement(b)
        setBiz(b)
        if (resp.data.length == 0) {
            message.error(`${b}报表没有数据`)
            return
        }
        let l = []
        for (let index = 0; index < resp.data.length; index++) {
            const element = resp.data[index];
            
            l.push({ version: element[0], value: parseFloat((element[5] / 1024 / 1024).toFixed(2)) })
        }
        setVersions(l)
        const curV = resp.data[resp.data.length - 1][0];
        await refreshTheVersion(b, curV);


    }
    useEffect(() => {
        clearPage()
        refreshPage(biz)

    }, []);
    const onChange = async (key) => {
        console.log(key, curVersion);
    };
    const onLinePointEvent = async (line, e) => {
        if (e.type === 'element:click') {
            if (!e.data || !e.data.data) return
            const { version } = e.data.data;
            if (!version) return
            message.info(`请求${version}版本数据`)
            clearTheVersion()
            console.log(version, curVersion, pieType);
            refreshTheVersion(biz, version);
        }
    }
    const onPieBlockEvent = async (pie, e) => {
        switch (e.type) {
            case 'label:click': {
                break;
            }
            case 'element:click': {
                if (!e.data || !e.data.data) return
                const { type, value: total_size } = e.data.data;
                setPieType(type);
                break;
            }
        }
    }
    let max_reduce_size13 = task13?.unused_assets?.reduce((prev, cur) => prev + cur.max_reduce_size, 0) / 1024
    let max_reduce_size10 = task10?.duplicated_files?.reduce((prev, cur) => prev + cur.size, 0) / 1024
    let max_reduce_size8 = task8?.uncompressed_file_types?.reduce((prev, cur) => prev + cur.total_size, 0) / 1024
    let max_reduce_size6 = task6?.non_alpha_pngs?.reduce((prev, cur) => prev + cur.max_reduce_size, 0) / 1024
    let total_max_reduce_size = (max_reduce_size13 + max_reduce_size10 + max_reduce_size8 + max_reduce_size6) / 1024
    return (

        // <PageContainer
            // tabList={tabs}
            // tabProps={{
            //     type: 'editable-card',
            //     hideAdd: true,
            //     onEdit: (e, action) => message.info("noting"),
            //     onChange: async (activeKey) => {
            //         console.log(activeKey)
            //         switch (activeKey) {
            //             case 'apk':
            //             case 'quickgame':
            //             case 'baidusmartapp':
            //                 clearPage()
            //                 await refreshPage(activeKey)
            //                 break
            //         }
            //     },
            // }}
            // >
            <ProCard>

                <Typography.Title level={4} style={{ margin: 0 }}>
                    最近半年<Text type="success">{tabs.filter(t => biz === t.key)[0].tab}</Text>大小的变化
                </Typography.Title>
                <Button icon={<SearchOutlined />}
                    onClick={(event) => {
                        switch (biz) {
                            case 'apk':
                                window.open('https://devops.cloud.oppoer.me/micro-app/ci/app/pipeline/execute?serverId=50897&psaId=50174&uuid=1256b920-faa5-425b-b53f-82be84de3268&appArrange=2&versionName=%E6%95%B4%E5%8C%85%E4%BD%93%E7%A7%AF%E6%A3%80%E6%B5%8B&projectId=1103893&projectType=3&latestWorkflowId=3660883&pipelineHistory=true')
                                break;
                            case 'quickgame':
                                window.open('https://devops.cloud.oppoer.me/micro-app/ci/app/pipeline/execute?serverId=50897&psaId=50174&projectId=1103893&latestWorkflowId=3625245&projectType=3')
                                break
                            case 'baidusmartapp':
                                window.open('https://devops.cloud.oppoer.me/micro-app/ci/app/pipeline/execute?serverId=50897&psaId=50174&projectId=1103893&projectType=3&latestWorkflowId=3633609&fromPipelineEdit=true')
                                break

                        }
                    }}>
                    重新执行<Text type="success">{tabs.filter(t => biz === t.key)[0].tab}</Text>分析流水线
                </Button>
                <Line
                    onEvent={onLinePointEvent}
                    onReady={async (line) => { }}
                    {...{
                        data: versions,
                        height: 400,
                        xField: 'version',
                        yField: 'value',
                        point: {
                            size: 5,
                            shape: 'diamond',
                            style: {
                                fill: 'white',
                                stroke: '#5B8FF9',
                                lineWidth: 2,
                            },
                        },
                        tooltip: {
                            showMarkers: true
                        },
                        state: {
                            active: {
                                animate: { duration: 100, easing: 'easeLinear' },
                                style: {
                                    shadowBlur: 4,
                                    stroke: 'red',
                                    fill: 'red',
                                },
                            },
                        },
                        interactions: [
                            {
                                type: 'marker-active',
                            },
                        ],
                    }} style={{
                        height: "200px"
                    }} />
                <Spin size="large"  spinning={loading}>
                    {sizePie1 &&
                        <>
                            {curVersion != 0 &&
                                <Row>
                                    <Typography.Title level={4} style={{ margin: 0 }}>
                                        {curVersion}版本详情
                                    </Typography.Title>
                                </Row>
                            }
                            <Row>
                                <Col span={10}>
                                    <P onEvent={onPieBlockEvent} data={sizePie1} />
                                </Col>
                                {pieType && sizePie2 &&
                                    <Col span={14}>
                                        <P data={sizePie2[pieType]} />
                                    </Col>
                                }
                            </Row>
                        </>
                    }
                    {/* <Typography.Title level={4} style={{ margin: 0 }}>
                列出每个版本可以优化的问题
            </Typography.Title> */}
                    {curVersion != 0 &&
                        <Typography.Title level={4} style={{ margin: 0 }}>
                            {curVersion}优化建议: 预计最小收益 {total_max_reduce_size.toFixed(2)}m
                        </Typography.Title>
                    }
                    <Collapse defaultActiveKey={['1']} onChange={onChange}>
                        {task3?.show_file_sizes?.length > 0 && 
                            <Panel header={`${task3?.task_description}\t\t数量:${task3?.show_file_sizes?.length}  超过10k的图片可以放到图床`} key={task3?.task_type}>
                                {task3?.show_file_sizes?.map((e) => <p>{e.entry_name}  {(e.entry_size / 1024).toFixed(2)}k</p>)}
                            </Panel>
                        }
                        {task14?.unstripped_lib?.length > 0 && 
                            <Panel header={`${task14?.task_description}\t\t数量:${task14?.unstripped_lib?.length}`} key={task14?.task_type}>
                                {task14?.unstripped_lib?.map((e) => <p>{e.name}</p>)}
                            </Panel>
                        }
                        {task7?.lib_dirs?.length > 0 && task7?.multi_lib &&
                            <Panel header={`${task7?.task_description}\t\t数量:${task7?.lib_dirs?.length}`} key={task7?.task_type}>
                                {task7?.lib_dirs?.map((e) => <p>{e.name}</p>)}
                            </Panel>
                        }
                        {task11?.stl_lib?.length > 0 && task11?.multi_stl &&
                            <Panel header={`${task11?.task_description}\t\t数量:${task11?.stl_lib?.length}`} key={task11?.task_type}>
                                {task11?.stl_lib?.map((e) => <p>{e.name}</p>)}
                            </Panel>
                        }
                        {task12?.unused_resources?.length > 0 &&
                            <Panel header={`${task12?.task_description}\t\t数量:${task12?.unused_resources?.length}`} key={task12?.task_type}>
                                {task12?.unused_resources?.map((e) => <p>{e.name}</p>)}
                            </Panel>
                        }
                        {task13?.unused_assets?.length > 0 &&
                            <Panel header={`${task13?.task_description}\t\t数量:${task13?.unused_assets?.length} 最大可减:${max_reduce_size13.toFixed(2)}k`} key={task13?.task_type}>
                                {task13?.unused_assets?.map((e) => <p>{e.name}</p>)}
                            </Panel>
                        }
                        {task10?.duplicated_files?.length > 0 &&
                            <Panel header={`${task10?.task_description}\t\t数量:${task10?.duplicated_files?.length}  最大可减:${max_reduce_size10.toFixed(2)}k`} key={task10?.task_type}>
                                {task10?.duplicated_files?.map((e) => <p>大小：{(e.size / 1024).toFixed(2)}k  {e.duplicated_file_paths.map((a) => <p>{a.name}</p>)}</p>)}
                            </Panel>
                        }
                        {task8?.uncompressed_file_types?.length > 0 &&
                            <Panel header={`${task8?.task_description}\t\t数量:${task8?.uncompressed_file_types?.length} 最大可减:${max_reduce_size8.toFixed(2)}k`} key={task8?.task_type}>
                                {task8?.uncompressed_file_types?.map((e) => <p>{e.suffix}  {(e.total_size / 1024).toFixed(2)}k</p>)}
                            </Panel>
                        }
                        {task6?.non_alpha_pngs?.length > 0 &&
                            <Panel header={`${task6?.task_description}\t\t数量:${task6?.non_alpha_pngs?.length} 最大可减:${max_reduce_size6.toFixed(2)}k`} key={task6?.task_type}>
                                {task6?.non_alpha_pngs?.map((e) => <p>{e.entry_name}  {(e.entry_size / 1024).toFixed(2)}k</p>)}
                            </Panel>
                        }
                    </Collapse>
                </Spin>
            </ProCard>
        // </PageContainer>
    )
}

export default ApkAnalyzer;