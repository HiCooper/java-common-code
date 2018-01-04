package com.berry.utils;


import com.berry.utils.encrypt.ConvertUtil;

import java.io.UnsupportedEncodingException;
import java.util.*;

public final class RandomUtil {
    /**
     * 所有字母和数字
     */
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 仅字母
     */
    public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 仅数字
     */
    public static final String NUMBERCHAR = "0123456789";


    /**
     * 生成制定范围内的随机数
     *
     * @param scopeMin
     * @param scoeMax
     * @return
     */
    public static int integer(int scopeMin, int scoeMax) {
        Random random = new Random();
        return (random.nextInt(scoeMax) % (scoeMax - scopeMin + 1) + scopeMin);
    }

    /**
     * 获取指定长度纯数字字符串
     *
     * @param length 长度
     * @return 长度为length的数字字符串
     */
    public static String getRandomNumByLength(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
        }
        return sb.toString();
    }


    /**
     * 返回一个定长的随机字符串(包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 长度为length的随机字符串
     */
    public static String getRandomStrByLength(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String getRandomCharByLength(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String LowerString(int length) {
        return getRandomCharByLength(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String UpperString(int length) {
        return getRandomCharByLength(length).toUpperCase();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num    数字
     * @param length 字符串长度
     * @return 定长的字符串
     */
    public static String toFixLengthString(int num, int length) {
        return String.format("%0" + length + "d", num);
    }

    /**
     * 每次生成的len位数都不相同
     *
     * @param param
     * @return 定长的数字
     */
    public static int getNotSimple(int[] param, int len) {
        Random rand = new Random();
        for (int i = param.length; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
    }

    /**
     * 从指定的数组中随机数组中的某个元素
     */
    public static <T> T randomItem(T[] param) {
        int index = integer(0, param.length);
        return param[index];
    }

    /**
     * 实现一个简单的字符串乘法
     *
     * @param str
     * @param multiplication
     * @return
     */
    private static String strMultiplication(String str, int multiplication) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < multiplication; i++) {
            buffer.append(str);
        }
        return buffer.toString();
    }

    /**
     * 从指定的数组中按照指定比例返回指定的随机元素
     *
     * @param param
     * @param percentum
     * @param <T>
     * @return
     */
    public static <T> T randomItem(T[] param, double[] percentum) {
        int length = percentum.length;
        Integer[] ints = ArrayUtil.doubleBitCount(percentum);
        int max = Collections.max(Arrays.asList(ints));
        int[] arr = new int[length];
        int sum = 0;
        Map map = new HashMap(length);
        int multiple = Integer.parseInt("1" + strMultiplication("0", max));
        for (int i = 0; i < length; i++) {
            int temp = (int) (percentum[i] * multiple);
            arr[i] = temp;
            if (i == 0) {
                map.put(i, new int[]{1, temp});
            } else {
                map.put(i, new int[]{sum, sum + temp});
            }
            sum += temp;
        }
        int indexSum = integer(1, sum);
        int index = -1;
        for (int i = 0; i < length; i++) {
            int[] scope = (int[]) map.get(i);
            if (indexSum == 1) {
                index = 0;
                break;
            }
            if (indexSum > scope[0] && indexSum <= scope[1]) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new RuntimeException("随机失败");
        } else {
            return param[index];
        }
    }

    /**
     * 返回一个UUID
     *
     * @return 小写的UUID
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        return s.substring(0, 8) + s.substring(9, 13) +
                s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 返回一个有序列的uuid编码
     * 前11位为时间(毫秒)
     * 中间4位为主机特征码
     * 剩下的保证其唯一性
     *
     * @return
     */
    public static String squid() {
        Long date = System.currentTimeMillis();
        String s = UUID.randomUUID().toString();
        String str = Long.toHexString(date);
        String result = str + SysUtil.HOST_IP
                + s.substring(17, 18) + s.substring(19, 23) + s.substring(24);
        return result.toUpperCase();
    }

    /**
     * 根据字符串生成密钥字节数组
     *
     * @param hexStr 十六进制字符串
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String hexStr) throws UnsupportedEncodingException {
        // 声明一个位的字节数组，默认里面都是0
        byte[] key = new byte[24];
        // 将字符串转成字节数组
        byte[] temp = hexStr.getBytes("UTF-8");

		/*
         * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
        if (key.length > temp.length) {
            // 如果temp不够位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            // 如果temp大于位，则拷贝temp数组个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        System.out.println("【十六进制24字节-密钥】：1A2B3C4D5E6F78901234");
        System.out.println("【ASCII-密钥】：" + ConvertUtil.bytesToHexString(key));

        return key;
    }

    /**
     * 随机生成32位字符串
     *
     * @return
     */
    public static String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
