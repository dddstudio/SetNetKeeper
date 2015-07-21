package com.example.fucknetkeeper;

import java.util.Date;

// Referenced classes of package cqxinli:
//			MD5

public class CXKUsername
{

	private int m_ver;
	private long m_lasttimec;
	private String m_username;
	private String m_realusername;
	private String RADIUS;
	private String LR;

	public static char trans(long n)
	{
		if (n <= 127L)
			return (char)(int)n;
		else
			return (char)(int)(n - 256L);
	}

	public static byte intToByte(long n)
	{
		if (n <= 127L)
			return (byte)(int)n;
		else
			return (byte)(int)(n - 256L);
	}

	public CXKUsername(String username)
	{
		m_ver = 18;
		m_lasttimec = 0L;
		m_username = username;
		RADIUS = "cqxinliradius002";
		LR = "\r\n";
	}

	public long GetLastTimeC()
	{
		return m_lasttimec;
	}

	public String Realusername()
	{
		long time = (new Date()).getTime();
		time /= 1000L;
		char ss[] = new char[4];
		byte by2[] = new byte[4];
		String m_formatsring = "";
		long t = time;
		t *= 0x66666667L;
		t >>= 32;
		t >>= 1;
		long m_time1c = t;
		m_lasttimec = m_time1c;
		t = m_time1c;
		by2[3] = intToByte(t & 255L);
		by2[2] = intToByte((t & 65280L) / 256L);
		by2[1] = intToByte((t & 0xff0000L) / 0x10000L);
		by2[0] = intToByte((t & 0xffffffffff000000L) / 0x1000000L);
		int t0 = 0;
		t0 = (int)m_time1c;
		int t1 = t0;
		int t2 = t0;
		int t3 = t0;
		t3 <<= 16;
		t1 &= 0xff00;
		t1 |= t3;
		t3 = t0;
		t3 &= 0xff0000;
		t2 >>= 16;
		t3 |= t2;
		t1 <<= 8;
		t3 >>= 8;
		t1 |= t3;
		long m_time1convert = t1;
		long tc = 0L;
		tc = m_time1convert;
		ss[3] = trans(tc & 255L);
		ss[2] = trans((tc & 65280L) / 256L);
		ss[1] = trans((tc & 0xff0000L) / 0x10000L);
		ss[0] = trans((tc & 0xffffffffff000000L) / 0x1000000L);
		char pp[] = new char[4];
		int i = 0;
		int j = 0;
		int k = 0;
		for (i = 0; i < 32; i++)
		{
			j = i / 8;
			k = 3 - i % 4;
			pp[k] *= '\002';
			if (ss[j] % 2 == 1)
				pp[k]++;
			ss[j] /= '\002';
		}

		char pf[] = new char[6];
		short st1 = (short)pp[3];
		st1 /= 4;
		pf[0] = trans(st1);
		st1 = (short)pp[3];
		st1 &= 3;
		st1 *= 16;
		pf[1] = trans(st1);
		short st2 = (short)pp[2];
		st2 /= 16;
		st2 |= st1;
		pf[1] = trans(st2);
		st1 = (short)pp[2];
		st1 &= 0xf;
		st1 *= 4;
		pf[2] = trans(st1);
		st2 = (short)pp[1];
		st2 /= 64;
		st2 |= st1;
		pf[2] = trans(st2);
		st1 = (short)pp[1];
		st1 &= 0x3f;
		pf[3] = trans(st1);
		st2 = (short)pp[0];
		st2 /= 4;
		pf[4] = trans(st2);
		st1 = (short)pp[0];
		st1 &= 3;
		st1 *= 16;
		pf[5] = trans(st1);
		for (int n = 0; n < 6; n++)
		{
			pf[n] += ' ';
			if (pf[n] >= '@')
				pf[n]++;
		}

		for (int m = 0; m < 6; m++)
			m_formatsring = (new StringBuilder(String.valueOf(m_formatsring))).append(pf[m]).toString();

		String strtem;
		if (m_username.contains("@"))
			strtem = m_username.substring(0, m_username.indexOf("@"));
		else
			strtem = m_username;
		String strInput = (new StringBuilder(String.valueOf(strtem))).append(RADIUS).toString();
		byte temp[] = new byte[by2.length + strInput.getBytes().length];
		System.arraycopy(by2, 0, temp, 0, by2.length);
		System.arraycopy(strInput.getBytes(), 0, temp, by2.length, strInput.getBytes().length);
		String m_md5 = MD5.getMD5(temp);
		String m_md5use = m_md5.substring(0, 2);
		m_realusername = (new StringBuilder(String.valueOf(m_formatsring))).append(m_md5use).append(m_username).toString();
		m_realusername = (new StringBuilder(String.valueOf(LR))).append(m_realusername).toString();
		return m_realusername;
	}
}
