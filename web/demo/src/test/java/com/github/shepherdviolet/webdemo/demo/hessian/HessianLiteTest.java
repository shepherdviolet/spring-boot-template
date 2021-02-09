package com.github.shepherdviolet.webdemo.demo.hessian;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.alibaba.com.caucho.hessian.io.SerializerFactory;
import org.junit.Test;
import sviolet.thistle.util.common.CloseableUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class HessianLiteTest {

    @Test
    public void test() throws IOException {

        //Bean
        Object bean = new Bean();

        //Create SerializerFactory and set allow non serializable
        SerializerFactory factory = new SerializerFactory();
        factory.setAllowNonSerializable(true);

        //To bytes
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        Hessian2Output hessianOutput = null;
        try {
            hessianOutput = new Hessian2Output(output);
            hessianOutput.setSerializerFactory(factory);
            hessianOutput.writeObject(bean);
        } finally {
            try {
                hessianOutput.close();
            } catch (Exception ignore){
            }
            CloseableUtils.closeQuiet(output);
        }

        InputStream input = new ByteArrayInputStream(output.toByteArray());
        Hessian2Input hessianInput = null;
        Object result = null;
        try {
            hessianInput = new Hessian2Input(input);
            hessianInput.setSerializerFactory(factory);
            result = hessianInput.readObject();
        } finally {
            try {
                hessianInput.close();
            } catch (Exception ignore){
            }
            CloseableUtils.closeQuiet(input);
        }

//        System.out.println(result);

    }

    public static class Bean {
        private float range = 0.87f;
        private BigDecimal amount = new BigDecimal("123.456");
        private Map<String, Object> map = new HashMap<>();

        public Bean() {
            map.put("1", new Item());
        }
    }

    public static class Item {
        private float range = 0.87f;
        private BigDecimal amount = new BigDecimal("123.456");
    }

}
