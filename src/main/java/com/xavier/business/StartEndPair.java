package com.xavier.business;

import lombok.Data;

/**
 * @description: 文件分片起始游标
 * @author: ex_wuzr11
 * @date: 2024/10/18 17:09
 */
@Data
public class StartEndPair {

	/**
	 * 开始游标
	 */
	public long start;

	/**
	 * 结束游标
	 */
	public long end;

	@Override
	public String toString() {
		return "start=" + start + "; end=" + end;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof StartEndPair)) return false;
		StartEndPair that = (StartEndPair) o;
		return start == that.start && end == that.end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (end ^ (end >>> 32));
		result = prime * result + (int) (start ^ (end >>> 32));
		return result;
	}

}
