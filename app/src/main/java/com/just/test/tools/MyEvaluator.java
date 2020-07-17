package com.just.test.tools;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class MyEvaluator implements TypeEvaluator<PointF>{

	private PointF pointF1;
	private PointF pointF2;


	public MyEvaluator(PointF pointF1,PointF pointF2){
		this.pointF1 = pointF1;
		this.pointF2 = pointF2;
	}
	
	
	@Override
	public PointF evaluate(float t, PointF startValue, PointF endValue) {
		PointF pointF = new PointF();
		pointF.x = startValue.x * (1 - t) * (1 - t) * (1 - t)
                + 3 * pointF1.x * t * (1 - t) * (1 - t)
                + 3 * pointF2.x * t * t * (1 - t)
                + endValue.x * t * t * t;
        pointF.y = startValue.y * (1 - t) * (1 - t) * (1 - t)
                + 3 * pointF1.y * t * (1 - t) * (1 - t)
                + 3 * pointF2.y * t * t * (1 - t)
                + endValue.y * t * t * t;
		return pointF;
	}

}
