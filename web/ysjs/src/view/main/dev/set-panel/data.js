import {
    reset,
    getModel,
    setModel,
    setTime,
    changeWater,
    manualClean,
    wash,
    stateSwitch,
    paramsSet,
    userParams
} from "@/service/api/set";

export let set_func = [
    { name: '机型设置', component: 'model-set', id: 'mode', type: 1, btnText: '切换到BJ', func: reset, desc: '模式设置' },
    { name: '恢复默认设置', component: 'config-reset', id: 'mode', type: 2, btnText: '切换到BJ', func: 'reset', desc: '将所有用户参数恢复到默认设置' },
    { name: '定时启动和取消', component: 'timeset-model', id: 'mode', type: 1, btnText: '切换到BJ', func: 'aaaaaa', desc: '模式设置' },
    // { name: '手动换水', component: 'change-water', id: 'mode', type: 1, btnText: '切换到BJ', func: 'aaaaaa', desc: '更换设备内储水' },
    // { name: '手动消毒', component: 'manual-clean', id: 'mode', type: 1, btnText: '切换到BJ', func: 'aaaaaa', desc: '对设备消毒' },
    // { name: '手动冲洗', component: 'manual-wash', id: 'mode', type: 1, btnText: '切换到BJ', func: 'aaaaaa', desc: '冲洗' },
    // { name: '手动复位净水量', component: 'water-reset', id: 'mode', type: 1, btnText: '切换到BJ', func: 'aaaaaa', desc: '复位净水量' },
    { name: '系统参数设置', component: 'sys-set', id: 'mode', type: 1, btnText: '切换到BJ', func: 'aaaaaa', desc: '设置系统参数' },
    { name: '用户参数设置', component: 'user-set', id: 'mode', type: 1, btnText: '切换到BJ', func: 'aaaaaa', desc: '设置用户参数' }
];