package com.example.region;

public class RegionEntriy {

//	public double leftTopLat=20.099786;
//	public double leftTopLng=110.050049;
//	public double rightBottomLat=19.277443;
//	public double rightBottomLng=110.717468;
	
	
	//海南省 
	//左上：108.535915299764，20.1897596945369
	//右下：111.18203555246，18.1374435618275

	
	public double leftTopLat=20.1897596945369;
	public double leftTopLng=108.535915299764;
	public double rightBottomLat=18.1374435618275;
	public double rightBottomLng=111.18203555246;
	
	public double  scrLeft=0;
	public double  scrTop=0;
	public double  scrRight=720;
	public double  scrBottom=1280;
	//经纬度区间
	private double dxLng=-1;
	private double dxScr=-1;
	private double dyLat=-1;
	private double dyScr=-1;
	
	
	public double getWigth(){
		return scrRight-scrLeft;
	}

	public double getHeight(){
		return scrBottom-scrTop;
	}
	
	
	public double changeX(double lng){
		if(dxLng==-1){
			dxLng=rightBottomLng-leftTopLng;
		}
		if(dxScr==-1){
			dxScr=scrRight-scrLeft;
		}
		
		double scrX=dxScr*(lng-leftTopLng)/dxLng+scrLeft;
		return scrX;
	}
	
	
	public double changeY(double lat){
		if(dyLat==-1){
			dyLat=rightBottomLat-leftTopLat;
		}
		if(dyScr==-1){
			dyScr=scrBottom-scrTop;
		}
		
		double scrY=dyScr*(lat-leftTopLat)/dyLat+scrTop;
		return scrY;
	}
	
}
