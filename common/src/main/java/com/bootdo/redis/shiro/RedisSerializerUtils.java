package com.bootdo.redis.shiro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.bootdo.utils.ArrayUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * redis的value序列化工具
 * 
 * @author created by zjw on 2019年2月14日 下午6:06:20
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class RedisSerializerUtils implements RedisSerializer {

	/**
	 * 序列化
	 */
	@Override
	public byte[] serialize(Object t) throws SerializationException {

		byte[] result = null;
		if (Objects.isNull(t)) {
			return new byte[0];
		}
		try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)) {
			if (!(t instanceof Serializable)) {
				throw new IllegalArgumentException(
						RedisSerializerUtils.class.getSimpleName() + " requires a Serializable payload "
								+ "but received an object of type [" + t.getClass().getName() + "]");
			}
			objectOutputStream.writeObject(t);
			objectOutputStream.flush();
			result = byteStream.toByteArray();
		} catch (Exception e) {
			log.error("Failed to serialize", e);
		}
		return result;
	}

	/**
	 * 反序列化
	 */
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {

		Object result = null;
		if (ArrayUtils.isEmpty(bytes)) {
			return null;
		}
		try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteStream)) {
			result = objectInputStream.readObject();
		} catch (Exception e) {
			log.error("Failed to deserialize", e);
		}
		return result;
	}
}