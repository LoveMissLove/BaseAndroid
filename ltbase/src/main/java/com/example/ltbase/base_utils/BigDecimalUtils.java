package com.example.ltbase.base_utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 作者：王建 on 2020/12/28 15:48
 */
public class BigDecimalUtils {
    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @param scale 保留scale 位小数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数不能小于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @param scale 保留scale 位小数
     * @return 两个参数的差
     */
    public static String sub(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数不能小于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数不能小于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数不能小于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数不能小于零");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 取余数
     *
     * @param v1 被除数
     * @param v2 除数
     * @param scale 小数点后保留几位
     * @return 余数
     */
    public static String remainder(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数不能小于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.remainder(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 比较大小
     *
     * @param v1 被比较数
     * @param v2 比较数
     * @return 如果v1 大于v2 则 返回true 否则false
     */
    public static boolean compare(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        int bj = b1.compareTo(b2);
        if (bj > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 向上取整
     * @param v1 取整数
     * */
    public static long moneyUp(String v1){
        BigDecimal bd = new BigDecimal(v1);
        long l  = bd.setScale( 0, BigDecimal.ROUND_UP ).longValue(); // 向上取整
        return l;
    }
    /**
     * 向下取整D
     * @param v1 取整数
     * */
    public static long moneyDown(String v1){
        BigDecimal bd = new BigDecimal(v1);
        long l  = bd.setScale( 0, BigDecimal.ROUND_DOWN  ).longValue(); // 向下取整
        return l;
    }

    /**
     * 解决纯数字自动读成科学计数法，例如12 自动转为 12.0
     */
    public static String customExcelNumericFormat(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        String temp = d + "";
        // 科学计数法中的n（10的n次方）
        int n = 0;
        // 判断有多少位有效小数
        int a = 0;
        // 如果该数字使用了科学计数法
        if (temp.indexOf("E") >= 0) {
            // 判断出要移多少位
            String auxiliaryStr = temp.split("E")[1];
            String realStr = temp.split("E")[0];
            n = Integer.parseInt(auxiliaryStr);
            // 有多少位有效小数（科学计数法）
            a = (realStr).length() - (realStr).indexOf(".") - 1 - n;
        } else {
            // 有多少位有效小数（非科学计数法）
            a = (d + "").length() - (d + "").indexOf(".") - 1;
        }
        if (a == 1 && (d + "").endsWith(".0")) {
            // 如果excel里本无小数是java读取时自动加的.0那就直接将小数位数设0
            nf.setMinimumFractionDigits(0);
        } else {
            // 有小数的按小数位数设置
            nf.setMinimumFractionDigits(a);
        }
        String s = nf.format(d);
        if (s.indexOf(",") >= 0) {
            s = s.replace(",", "");
        }
        return s;
    }

    /**
     * 金额为分的格式
     */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 将分为单位的转换为元并返回金额格式的字符串 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(Long amount) throws Exception {
        if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }

        int flag = 0;
        String amString = amount.toString();
        if (amString.charAt(0) == '-') {
            flag = 1;
            amString = amString.substring(1);
        }
        StringBuffer result = new StringBuffer();
        if (amString.length() == 1) {
            result.append("0.0").append(amString);
        } else if (amString.length() == 2) {
            result.append("0.").append(amString);
        } else {
            String intString = amString.substring(0, amString.length() - 2);
            for (int i = 1; i <= intString.length(); i++) {
                if ((i - 1) % 3 == 0 && i != 1) {
                    result.append(",");
                }
                result.append(intString.substring(intString.length() - i, intString.length() - i + 1));
            }
            result.reverse().append(".").append(amString.substring(amString.length() - 2));
        }
        if (flag == 1) {
            return "-" + result.toString();
        } else {
            return result.toString();
        }
    }

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(Context context, String amount) {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            Toast.makeText(context, "金额格式有误", Toast.LENGTH_LONG).show();
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static String changeY2F(Long amount) {
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
    }

    /**
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\¥|\\,", "");  //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0L;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

    /**
     * 限制小数点后两位
     *
     * EditText属性改成如下：
     * numberDecimal表示只能输入一个小数点
     *
     *             <EditText
     *                 android:id="@+id/et_amt"
     *                 android:layout_width="wrap_content"
     *                 android:layout_height="wrap_content"
     *                 android:layout_marginLeft="20dp"
     *                 android:background="@null"
     *                 android:layout_weight="1"
     *                 android:layout_gravity="center_vertical"
     *                 android:textColorHint="@color/font_CCCCCC"
     *                 android:textColor="@color/font_333333"
     *                 android:textSize="14sp"
     *                 android:paddingTop="20dp"
     *                 android:paddingBottom="20dp"
     *                 android:inputType="number|numberDecimal"
     *                 android:hint="需小于或等于本期金额"/>
     *
     */
    public static void limitTwoDecimal(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }
}