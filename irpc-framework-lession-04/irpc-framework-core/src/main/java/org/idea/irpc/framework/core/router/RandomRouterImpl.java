package org.idea.irpc.framework.core.router;

import org.idea.irpc.framework.core.common.ChannelFutureWrapper;
import org.idea.irpc.framework.core.registy.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.*;

/**
 * 随机筛选
 *
 * @Author linhao
 * @Date created in 8:26 下午 2022/1/5
 */
public class RandomRouterImpl implements IRouter {


    @Override
    public void refreshRouterArr(Selector selector) {
        //获取服务提供者的数目
        List<ChannelFutureWrapper> channelFutureWrappers = CONNECT_MAP.get(selector.getProviderServiceName());
        ChannelFutureWrapper[] arr = new ChannelFutureWrapper[channelFutureWrappers.size()];
        //提前生成调用先后顺序的随机数组
        int[] result = createRandomIndex(arr.length);
        //生成对应服务集群的每台机器的调用顺序
        for (int i = 0; i < result.length; i++) {
            arr[i] = channelFutureWrappers.get(result[i]);
        }
        SERVICE_ROUTER_MAP.put(selector.getProviderServiceName(), arr);
    }

    @Override
    public ChannelFutureWrapper select(Selector selector) {
        return CHANNEL_FUTURE_POLLING_REF.getChannelFutureWrapper(selector.getProviderServiceName());
    }

    @Override
    public void updateWeight(URL url) {
        //服务节点的权重
        List<ChannelFutureWrapper> channelFutureWrappers = CONNECT_MAP.get(url.getServiceName());
        Integer[] weightArr = createWeightArr(channelFutureWrappers);
        Integer[] finalArr = createRandomArr(weightArr);
        ChannelFutureWrapper[] finalChannelFutureWrappers = new ChannelFutureWrapper[finalArr.length];
        for (int j = 0; j < finalArr.length; j++) {
            finalChannelFutureWrappers[j] = channelFutureWrappers.get(finalArr[j]);
        }
        SERVICE_ROUTER_MAP.put(url.getServiceName(),finalChannelFutureWrappers);
    }

    private static Integer[] createWeightArr(List<ChannelFutureWrapper> channelFutureWrappers) {
        List<Integer> weightArr = new ArrayList<>();
        for (int k = 0; k < channelFutureWrappers.size(); k++) {
            Integer weight = channelFutureWrappers.get(k).getWeight();
            int c = weight / 100;
            for (int i = 0; i < c; i++) {
                weightArr.add(k);
            }
        }
        Integer[] arr = new Integer[weightArr.size()];
        return weightArr.toArray(arr);
    }

    public static void main(String[] args) {
        List<ChannelFutureWrapper> channelFutureWrappers = new ArrayList<>();
        channelFutureWrappers.add(new ChannelFutureWrapper(null, null, 100));
        channelFutureWrappers.add(new ChannelFutureWrapper(null, null, 200));
        channelFutureWrappers.add(new ChannelFutureWrapper(null, null, 9300));
        channelFutureWrappers.add(new ChannelFutureWrapper(null, null, 400));
        Integer[] r = createWeightArr(channelFutureWrappers);
        System.out.println(r);
    }

    /**
     * 创建随机乱序数组
     *
     * @param arr
     * @return
     */
    private static Integer[] createRandomArr(Integer[] arr) {
        int total = arr.length;
        Random ra = new Random();
        for (int i = 0; i < total; i++) {
            int j = ra.nextInt(total);
            if (i == j) {
                continue;
            }
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    private int[] createRandomIndex(int len) {
        int[] arrInt = new int[len];
        Random ra = new Random();
        for (int i = 0; i < arrInt.length; i++) {
            arrInt[i] = -1;
        }
        int index = 0;
        while (index < arrInt.length) {
            int num = ra.nextInt(len);
            //如果数组中不包含这个元素则赋值给数组
            if (!contains(arrInt, num)) {
                arrInt[index++] = num;
            }
        }
        return arrInt;
    }


    public boolean contains(int[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return true;
            }
        }
        return false;
    }


}
