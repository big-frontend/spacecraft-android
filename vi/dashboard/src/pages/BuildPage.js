import React, { Component } from 'react';
import { Line } from '@ant-design/charts';
import { Pie } from '@ant-design/plots';
import { PageContainer } from '@ant-design/pro-components';
const BuildPage = () => {

    const data = [
        {
            type: '分类一',
            value: 27,
        },
        {
            type: '分类二',
            value: 25,
        },
        {
            type: '分类三',
            value: 18,
        },
        {
            type: '分类四',
            value: 15,
        },
        {
            type: '分类五',
            value: 10,
        },
        {
            type: '其他',
            value: 5,
        },
    ];
    const config = {
        appendPadding: 10,
        data,
        angleField: 'value',
        colorField: 'type',
        radius: 0.75,
        label: {
            type: 'spider',
            labelHeight: 28,
            content: '{name}\n{percentage}',
        },
        interactions: [
            {
                type: 'element-active',
            },
            {
                type: 'element-active',
            },
        ],
    };
    const lineConfig = {
        data: [
            { year: '1991', value: 3 },
            { year: '1992', value: 4 },
            { year: '1993', value: 3.5 },
            { year: '1994', value: 5 },
            { year: '1995', value: 4.9 },
            { year: '1996', value: 6 },
            { year: '1997', value: 7 },
            { year: '1998', value: 9 },
            { year: '1999', value: 13 },
        ],
        height: 400,
        xField: 'year',
        yField: 'value',
        point: {
            size: 5,
            shape: 'diamond',
        },
    };
    return (
        <div style={{ height: "100%" }}>
            <p>1.每个apk版本size的变化</p>
            <Line {...lineConfig} style={{
                    height: "200px"
                }} />
            <p>2.列出每个版本可以优化的问题</p>

            <p>3.版本详情</p>
            <Pie {...config} style={{
                height: "500px"
            }} />
            <p>4.优化建议</p>
        </div>
    )
}

export default BuildPage;