package lhr.plist;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lhr.content.Frame;
import lhr.content.Frames;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ExtractMs {
	public static void main(String[] args) throws Exception {
		Element dict = null;//ָ��ÿһ��dictԪ��
		
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File("doc/2.plist"));
		Element root = doc.getRootElement();//��ø��ڵ�plist
		//System.out.println(root.getName());
		
		dict= root.getChild("dict");//��Ԫ�ؽ����ŵ�dict��
		
		List<Element> list_key = dict.getChildren("key");//���frames��metadataԪ����
		//Frames����������Ż�ȡ��ÿһ֡ͼƬ
		Frames frs = new Frames(); 
		/*��ȡframes�ж�Ӧ��ͼ�����Frames�����У���ȡԪ������Ϣ*/
		Element e = null;
		for(int i=0;i<list_key.size();i++){
			e = list_key.get(i);
			if("frames".equals(e.getText())){
				dict = e.getParentElement().getChild("dict");
				frs.setFrames(dict.getChildren("key"));//��ȡһ֡һ֡��ͼƬͷ��Ϣ����key��Ϣ��
			}else if("metadata".equals(e.getText())){
				
			}
		}
		frs.setFrame_num(frs.frames.size());//ͼƬ����
		Frame fr = new Frame();
		setFrameMs(fr,frs);
		//��������������Դ洢�Ƿ�ɹ�
		System.out.println(frs.frame.get(2).picName);
		System.out.println(frs.frame.get(2).height);
		System.out.println(frs.frame.get(2).roated);
		
	}
	/**
	 * ������ÿһ����ͼƬ�ֿ��洢��Frames���������List<Frame>����ȥ,
	 * ÿһ��ͼƬ����Ϣ�洢��Frame����������
	 * 
	 */
	public static void setFrameMs(Frame fr,Frames frs){
		
		List<Element> list_dict = frs.frames.get(0).getParentElement().getChildren("dict");//�ֱ��ȡ�÷�ͼƬ����Ӧ��dict��
	
		List<Element> list_dict_key = new ArrayList<Element>();
		List<Element> list_dict_value = new ArrayList<Element>();
		//List<Element> list_dict_value = new ArrayList<Element>();
		
		for(int i=0;i<frs.getFrame_num();i++){
			fr.setPicName(frs.frames.get(i).getText());//ͼƬ����
			//System.out.println(fr.getPicName());
			list_dict_key = list_dict.get(i).getChildren("key");//�÷�ͼƬ��Ӧ��dict���е�key�б�
			list_dict_value = list_dict.get(i).getChildren("string");//�÷�ͼƬ��Ӧ��dict���е�string�б�
			//System.out.println(list_dict_value.size());
			String key = null;
			String value = null;
			int k = 0;//��������rotated���Ԫ��
			for(int j=0;j<list_dict_key.size();j++,k++){
				
				key = list_dict_key.get(j).getText();
				if("rotated".equals(key)){
					if(null == list_dict.get(i).getChild("false")){
						fr.setRoated(true);
					}else{
						fr.setRoated(false);
					}
					k--;//��key��ֵΪrotated��ʱ��list_key_value�в�����ֵ�����Ա���λ�ò��䡣
				}else{
					value = list_dict_value.get(k).getText();
				}
				
				if("frame".equals(key)||"sourceColorRect".equals(key)){
					String value1 = value.substring(value.indexOf("{{")+2,value.indexOf("},"));//value1�м�¼��ͼ����ʼ����
					String value2 = value.substring(value.indexOf(",{")+2, value.indexOf("}}"));//value2�м�¼��ͼ�񳤺Ϳ�
					String sX = value1.substring(0,value1.indexOf(","));//��ʼ������
					String sY = value1.substring(value1.indexOf(",")+1);//��ʼ������
					String wd = value2.substring(0, value2.indexOf(","));
					String hg = value2.substring(value2.indexOf(",")+1);
					if("frame".equals(key)){//��������ͼ�����ʼ����ʹ�С
						fr.setStartX(sX);
						fr.setStartY(sY);
						fr.setWidth(wd);
						fr.setHeight(hg);
					}else{//���÷�͸��ͼ�������ʹ�С
						fr.setOpX(sX);
						fr.setOpY(sY);
						fr.setOpWidth(wd);
						fr.setOpHeight(hg);
					}
				}else if("offset".equals(key)){//ͼ�����򣨷�͸���������ĵ�����������͸���������͸���������ĵ��ƫ����
					String oX = value.substring(value.indexOf("{")+1, value.indexOf(","));
					String oY = value.substring(value.indexOf(",")+1, value.indexOf("}"));
					fr.setOffsetX(oX);
					fr.setOffsetY(oY);
				}
				
			}
			frs.frame.add(fr);//��Frame�����fr�洢��ͼƬ��Ϣ����Frames�ඨ���List<Frame>��,��������ķָ���ȡ
		}
	}
}
