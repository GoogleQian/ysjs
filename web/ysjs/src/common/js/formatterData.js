export const formatterData = function (data, key = "deptId") {
    data.forEach(item => {
        let parentId = item.parentId;
        if (parentId === 0) {
            //是根元素的hua ,不做任何操作
        } else {
            //如果item是子元素的话 ,把item扔到他的父亲的child数组中.
            data.forEach(d => {
                if (d[key] === parentId) {
                    let childArray = d.children;
                    if (!childArray) {
                        childArray = []
                    }
                    childArray.push(item);
                    d.children = childArray;
                }
            })
        }
    });
    //去除重复元素
    data = data.filter(item => item.parentId === 0);
    return data;
}