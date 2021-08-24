package utils;



import log.LogSystem;
import log.LogSystemFactory;
import lombok.SneakyThrows;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单工具类
 *
 * @ClassName : utils.base.simpleUtils
 * @Description :
 * @Date 2021-03-31 20:11:49
 * @Author ZhangHL
 */
public class SimpleUtils {

    private static LogSystem log = LogSystemFactory.getLogSystem();


    /**
     * 判断字符串是否为空
     *
     * @return 是则返回true，否则返回false
     * @class SimpleUtils
     * @Description //TODO
     * @Param
     * @Author Zhang huai lan
     * @Date 20:20 2021/3/31
     **/
    public static boolean isEmptyString(String str) {
        return (str == null || "".equals(str));
    }

    /**
     * 固定使用TimeFormatter.SEC_LEVEL
     *
     * @return
     * @class *ClassName*
     * @Description //TODO
     * @Param
     * @Author Zhang huai lan
     * @Date 21:23 2021/3/31
     **/
    public static String getTimeStamp() {
        return LocalDateTime.now().toString();
    }

}
