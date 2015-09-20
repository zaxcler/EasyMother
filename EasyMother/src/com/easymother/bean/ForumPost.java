package com.easymother.bean;

import java.util.Date;
public class ForumPost {
	 private Integer id;

	    private String createUser;

	    private Date createTime;

	    private String updateUser;

	    private Date updateTime;

	    private String more1;

	    private String more2;

	    private String more3;

	    private Integer floor;

	    private Integer int2;

	    private Integer sorting;

	    private String title;

	    private String images;

	    private Integer userId;
	    
	    private String  userNickname;
	    
	    private String  userImage;

	    private Integer blockId;

	    private Integer parentId;

	    private Integer parentUserId;
	    private String  parentUserNickname;
	    
	    private String parentCountent;
	    
//	    private String  ;

	    private Byte parentUserSee;

	    //  ,1, 2, 4, 7,11,
	    private String treePath;
	    
	    private int grade;

	    private Byte isDelete;

	    // KJ(空间)  HT(话题),  QZ(求助)
	    private String type;  // 

	    //  职务表id
	    private Integer nurseId;

	    private String content;
	    

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getCreateUser() {
	        return createUser;
	    }

	    public void setCreateUser(String createUser) {
	        this.createUser = createUser == null ? null : createUser.trim();
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public String getUpdateUser() {
	        return updateUser;
	    }

	    public void setUpdateUser(String updateUser) {
	        this.updateUser = updateUser == null ? null : updateUser.trim();
	    }

	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }

	    public String getMore1() {
	        return more1;
	    }

	    public void setMore1(String more1) {
	        this.more1 = more1 == null ? null : more1.trim();
	    }

	    public String getMore2() {
	        return more2;
	    }

	    public void setMore2(String more2) {
	        this.more2 = more2 == null ? null : more2.trim();
	    }

	    public String getMore3() {
	        return more3;
	    }

	    public void setMore3(String more3) {
	        this.more3 = more3 == null ? null : more3.trim();
	    }

	    
	  
	    public Integer getFloor() {
			return floor;
		}

		public void setFloor(Integer floor) {
			this.floor = floor;
		}

		public Integer getInt2() {
	        return int2;
	    }

	    public void setInt2(Integer int2) {
	        this.int2 = int2;
	    }

	    public Integer getSorting() {
	        return sorting;
	    }

	    public void setSorting(Integer sorting) {
	        this.sorting = sorting;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title == null ? null : title.trim();
	    }

	    public String getImages() {
	        return images;
	    }

	    public void setImages(String images) {
	        this.images = images == null ? null : images.trim();
	    }

	    public Integer getUserId() {
	        return userId;
	    }

	    public void setUserId(Integer userId) {
	        this.userId = userId;
	    }

	    public Integer getBlockId() {
	        return blockId;
	    }

	    public void setBlockId(Integer blockId) {
	        this.blockId = blockId;
	    }

	    
	    public String getParentCountent() {
			return parentCountent;
		}

		public void setParentContent(String parentCountent) {
			this.parentCountent = parentCountent;
		}

		public void setGrade(int grade) {
			this.grade = grade;
		}

		public Integer getParentId() {
	        return parentId;
	    }

	    public void setParentId(Integer parentId) {
	        this.parentId = parentId;
	    }

	    public Integer getParentUserId() {
	        return parentUserId;
	    }

	    public void setParentUserId(Integer parentUserId) {
	        this.parentUserId = parentUserId;
	    }

	    public Byte getParentUserSee() {
	        return parentUserSee;
	    }

	    public void setParentUserSee(Byte parentUserSee) {
	        this.parentUserSee = parentUserSee;
	    }

	    public String getTreePath() {
	        return treePath;
	    }

	    public void setTreePath(String treePath) {
	        this.treePath = treePath == null ? null : treePath.trim();
	    }

	    public Integer getGrade() {
	        return grade;
	    }

	    public void setGrade(Integer grade) {
	        this.grade = grade;
	    }

	    public Byte getIsDelete() {
	        return isDelete;
	    }

	    public void setIsDelete(Byte isDelete) {
	        this.isDelete = isDelete;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type == null ? null : type.trim();
	    }

	    public Integer getNurseId() {
	        return nurseId;
	    }

	    public void setNurseId(Integer nurseId) {
	        this.nurseId = nurseId;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content == null ? null : content.trim();
	    }

		public ForumPost() {
			super();
		}

		public ForumPost(String title, String images, Integer blockId, Integer parentId, Integer parentUserId, Byte parentUserSee, String treePath,
				Integer grade, Byte isDelete) {
			super();
			this.title = title;
			this.images = images;
			this.blockId = blockId;
			this.parentId = parentId;
			this.parentUserId = parentUserId;
			this.parentUserSee = parentUserSee;
			this.treePath = treePath;
			this.grade = grade;
			this.isDelete = isDelete;
		}

		public String getUserNickname() {
			return userNickname;
		}

		public void setUserNickname(String userNickname) {
			this.userNickname = userNickname;
		}

		public String getUserImage() {
			return userImage;
		}

		public void setUserImage(String userImage) {
			this.userImage = userImage;
		}

		public String getParentUserNickname() {
			return parentUserNickname;
		}

		public void setParentUserNickname(String parentUserNickname) {
			this.parentUserNickname = parentUserNickname;
		}

		@Override
		public String toString() {
			return "ForumPost [id=" + id + ", createUser=" + createUser + ", createTime=" + createTime + ", updateUser="
					+ updateUser + ", updateTime=" + updateTime + ", more1=" + more1 + ", more2=" + more2 + ", more3="
					+ more3 + ", floor=" + floor + ", int2=" + int2 + ", sorting=" + sorting + ", title=" + title
					+ ", images=" + images + ", userId=" + userId + ", userNickname=" + userNickname + ", userImage="
					+ userImage + ", blockId=" + blockId + ", parentId=" + parentId + ", parentUserId=" + parentUserId
					+ ", parentUserNickname=" + parentUserNickname + ", parentCountent=" + parentCountent
					+ ", parentUserSee=" + parentUserSee + ", treePath=" + treePath + ", grade=" + grade + ", isDelete="
					+ isDelete + ", type=" + type + ", nurseId=" + nurseId + ", content=" + content + "]";
		}
		
}