package com.nwgjb.pathfinder.util;
/**
 * A representation of tuple.
 * 
 * @author Gary
 *
 */
public class Tuple {
	public static class _2<T1, T2> extends Tuple{
		public T1 _1;
		public T2 _2;
		
		public _2(T1 _1, T2 _2){
			this._1=_1;
			this._2=_2;
		}
	}
	
	public static <T1, T2> _2<T1, T2> as(T1 _1, T2 _2){
		return new _2<>(_1, _2);
	}
}
