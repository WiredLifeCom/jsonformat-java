package com.wiredlife.jsonformatjava.utility;

public class OSValidator {

	public enum OS {
		WINDOWS, MAC, UNIX, SOLARIS, ANDROID;
	}

	public static OS getOS() {
		String os = System.getProperty("os.name");

		if (os == null) {
			throw new NullPointerException("os.name is null");
		}

		if (os.indexOf("win") >= 0 || os.indexOf("Win") >= 0) {
			return OS.WINDOWS;
		}
		if (os.indexOf("mac") >= 0 || os.indexOf("Mac") >= 0) {
			return OS.MAC;
		}
		if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0) {
			String jvm = System.getProperty("java.vm.name");

			if (jvm == null) {
				throw new NullPointerException("java.vm.name is null");
			}

			if (jvm.indexOf("dalvik") >= 0 || jvm.indexOf("Dalvik") >= 0) {
				return OS.ANDROID;
			}
			return OS.UNIX;
		}
		if (os.indexOf("sunos") >= 0) {
			return OS.SOLARIS;
		}

		throw new NullPointerException();
	}

}
