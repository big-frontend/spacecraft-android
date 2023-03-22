import { Pie, G2 } from '@ant-design/plots';
const G = G2.getEngine('canvas');
function P({...restProps}){
    return <Pie
    {...restProps}
    {...{
        appendPadding: 10,
        angleField: 'value',
        colorField: 'type',
        radius: 0.75,
        legend: false,
        theme: 'default', // 'dark',
        label: {
            type: 'spider',
            labelHeight: 40,
            formatter: (data, mappingData) => {
                let t = ''
                if (data.value / 1024 <= 1024) {
                    t = (data.value / 1024).toFixed(2) + 'k'
                } else if (data.value / 1024 > 1024) {
                    t = (data.value / 1024 / 1024).toFixed(2) + 'm'
                } else if (data.value / 1024 < 1024) {
                    t = (data.value).toFixed(2) + 'b'
                }
                const group = new G.Group({});
                group.addShape({
                    type: 'circle',
                    attrs: {
                        x: 0,
                        y: 0,
                        width: 40,
                        height: 50,
                        r: 5,
                        fill: mappingData.color,
                    },
                });
                group.addShape({
                    type: 'text',
                    attrs: {
                        x: 10,
                        y: 8,
                        text: `${data.type}`,
                        fill: mappingData.color,
                    },
                });
                group.addShape({
                    type: 'text',
                    attrs: {
                        x: 0,
                        y: 25,
                        text: `${t}    ${(data.percent * 100).toFixed(2)}%`,
                        fill: 'rgba(0, 0, 0, 0.65)',
                        fontWeight: 700,
                    },
                });
                return group;
            },
        },
        tooltip: {
            formatter: (data) => {
                let t = ''
                if (data.value / 1024 <= 1024) {
                    t = (data.value / 1024).toFixed(2) + 'k'
                } else if (data.value / 1024 > 1024) {
                    t = (data.value / 1024 / 1024).toFixed(2) + 'm'
                } else if (data.value / 1024 < 1024) {
                    t = (data.value).toFixed(2) + 'b'
                }
                console.log(data)
              return { name: data.type, value: t };
            },
        },
        interactions: [
            {
                type: 'element-selected',
            },
            {
                type: 'element-active',
            },
        ],
    }} style={{
        height: "500px"
    }} />
}
export default P;