package com.easymother.bean;

import java.util.List;

public class DetailResult {
	
		private int mediaAmount;

		private NurseJobBean nursejob;

		private List<Skill> skills ;

		private int forumPostAmount;

		private int scannerAmount;

		private int orderCommentAmunt;

		private List<Order> orderss ;

		private List<OrderComments> ordercommentss ;

		private List<Certificates> certificatess ;

		private NurseBaseBean nursebase;

		public void setMediaAmount(int mediaAmount){
		this.mediaAmount = mediaAmount;
		}
		public int getMediaAmount(){
		return this.mediaAmount;
		}
		public void setNursejob(NurseJobBean nursejob){
		this.nursejob = nursejob;
		}
		public NurseJobBean getNursejob(){
		return this.nursejob;
		}
		public void setSkyiies(List<Skill> skills){
		this.skills = skills;
		}
		public List<Skill> getSkyiies(){
		return this.skills;
		}
		public void setForumPostAmount(int forumPostAmount){
		this.forumPostAmount = forumPostAmount;
		}
		public int getForumPostAmount(){
		return this.forumPostAmount;
		}
		public void setScannerAmount(int scannerAmount){
		this.scannerAmount = scannerAmount;
		}
		public int getScannerAmount(){
		return this.scannerAmount;
		}
		public void setOrdercommentAmunt(int ordercommentAmunt){
		this.orderCommentAmunt = ordercommentAmunt;
		}
		public int getOrdercommentAmunt(){
		return this.orderCommentAmunt;
		}
		public void setOrders(List<Order> orders){
		this.orderss = orders;
		}
		public List<Order> getOrders(){
		return this.orderss;
		}
		public void setOrdercomments(List<OrderComments> ordercomments){
		this.ordercommentss = ordercomments;
		}
		public List<OrderComments> getOrdercomments(){
		return this.ordercommentss;
		}
		public void setCertificates(List<Certificates> certificates){
		this.certificatess = certificates;
		}
		public List<Certificates> getCertificates(){
		return this.certificatess;
		}
		public void setNursebase(NurseBaseBean nursebase){
		this.nursebase = nursebase;
		}
		public NurseBaseBean getNursebase(){
		return this.nursebase;
		}
		@Override
		public String toString() {
			return "DetailResult [mediaAmount=" + mediaAmount + ", nursejob=" + nursejob + ", skills=" + skills
					+ ", forumPostAmount=" + forumPostAmount + ", scannerAmount=" + scannerAmount
					+ ", orderCommentAmunt=" + orderCommentAmunt + ", orderss=" + orderss + ", ordercommentss="
					+ ordercommentss + ", certificatess=" + certificatess + ", nursebase=" + nursebase + "]";
		}
		


}
