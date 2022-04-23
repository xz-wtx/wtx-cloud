import { ref, onMounted, onUnmounted } from 'vue'

// 1. 定义一个函数,抽离逻辑，命名使用 useXXX
function useMousePosition() {
    // 使用ref定义
    const x = ref(0)
    const y = ref(0)

    function update(e) {
        console.log(x.value, y.value);

        x.value = e.pageX
        y.value = e.pageY
    }

    onMounted(() => {
        console.log('开始监听鼠标划动事件');
        window.addEventListener('mousemove', update)
    })

    onUnmounted(() => {
        console.log('解除监听鼠标划动事件');
        window.removeEventListener('mousemove', update)
    })
    return {
        x,
        y
    }
}


// 导出这个函数
export default useMousePosition
