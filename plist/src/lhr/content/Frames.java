package lhr.content;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

public class Frames {
	public List<Frame> frame = new ArrayList<Frame>();//���ڴ�Ż�ȡ��ÿһ֡ͼ��
	public List<Element> frames = new ArrayList<Element>();//���ÿ��ͼƬ��key��Ϣ���ļ���
	public int frame_num;//��ŵ�ͼ������
	
	public List<Frame> getFrame() {
		return frame;
	}
	public void setFrame(List<Frame> frame) {
		this.frame = frame;
	}
	public List<Element> getFrames() {
		return frames;
	}
	public void setFrames(List<Element> frames) {
		this.frames = frames;
	}
	public int getFrame_num() {
		return frame_num;
	}
	public void setFrame_num(int frame_num) {
		this.frame_num = frame_num;
	}

	
}
