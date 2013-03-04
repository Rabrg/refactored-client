package com.runescape.loader;

import java.io.DataInputStream;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassLoader extends java.lang.ClassLoader {

	private final Hashtable<String, Class<?>> cache;
	public ZipFile zip;
	public Class<?> link;

	@Override
	public final synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class<?> clazz = cache.get(name);
		if (clazz != null) {
			return clazz;
		}
		try {
			clazz = super.findSystemClass(name);
			return clazz;
		} catch (ClassNotFoundException _ex) {
		}
		if (name.indexOf("signlink") != -1) {
			return link;
		}
		try {
			ZipEntry zipEntry = zip.getEntry(name.replace('.', '/') + ".class");
			int size = (int) zipEntry.getSize();
			DataInputStream in = new DataInputStream(zip.getInputStream(zipEntry));
			byte buffer[] = new byte[size];
			in.readFully(buffer);
			in.close();
			clazz = defineClass(name, buffer, 0, buffer.length);
			if (resolve) {
				resolveClass(clazz);
			}
			cache.put(name, clazz);
		} catch (Exception _ex) {
		}
		return clazz;
	}

	public ClassLoader() {
		cache = new Hashtable<String, Class<?>>();
	}
}