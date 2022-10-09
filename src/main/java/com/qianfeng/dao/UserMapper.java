package com.qianfeng.dao;

import com.qianfeng.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /**
     * 分页查询
     * @param record:起始位置
     * mybatis一次传入多个参数：1. 将多个参数封装成对象传入  2. 单独分开传多个参数时，要加@Param("")标识参数
     * @return
     */
    List<User> getUserListByPage(User record);

    /**
     * 总条数
     * @return
     */
    Integer selectCount(User record);

    /**
     * 通过用户编码查数据
     * @param record
     * @return
     */
    User selectByUsercode(User record);

    /**
     * 获得当前时间
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }
    public static Date parse_2(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(strDate);
    }

    /**
     * 根据出生日期与当前系统时间计算年龄
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一
            }
        }
        if (age >= 0)
            return age;
        else
            return -1;
    }
    /**
     * 根据id批量删除用户
     * @param ids
     */
    void deleteBatch(@Param("ids") Integer[] ids);


    void updateByPrimaryKey_pass(User record);
}