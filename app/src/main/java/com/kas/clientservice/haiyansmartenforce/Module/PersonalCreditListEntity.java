package com.kas.clientservice.haiyansmartenforce.Module;

import java.util.List;

/**
 * 描述：
 * 时间：2018-08-03
 * 公司：COMS
 */

public class PersonalCreditListEntity {

        /**
         * AreaName : 新海社区
         * SchemeId : 2
         * SchemeName : 新海社区德信用
         * Base : 80
         * Get : 2
         * Lost : 0
         * Id : 227
         * Name : 陈元祥
         * Identity : 330424196301124055
         * IsPartyMember : false
         * JoinPartyDate : null
         * Lon : null
         * Lat : null
         * Sex : null
         * AreaCode : 330424002007
         * Address : 港湾花苑35幢401室
         * Tel : 13616735372
         * GetDetail : [{"Name":"积极参加社区文体活动","Type":2,"CreateDate":"2018-08-01","Comment":null,"Score":2}]
         * LostDetail : []
         */

        private String AreaName;
        private int SchemeId;
        private String SchemeName;
        private int Base;
        private int Get;
        private int Lost;
        private int Id;
        private String Name;
        private String Identity;
        private boolean IsPartyMember;
        private Object JoinPartyDate;
        private Object Lon;
        private Object Lat;
        private Object Sex;
        private String AreaCode;
        private String Address;
        private String Tel;
        private List<GetDetailBean> GetDetail;
        private List<GetDetailBean> LostDetail;

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public int getSchemeId() {
            return SchemeId;
        }

        public void setSchemeId(int SchemeId) {
            this.SchemeId = SchemeId;
        }

        public String getSchemeName() {
            return SchemeName;
        }

        public void setSchemeName(String SchemeName) {
            this.SchemeName = SchemeName;
        }

        public int getBase() {
            return Base;
        }

        public void setBase(int Base) {
            this.Base = Base;
        }

        public int getGet() {
            return Get;
        }

        public void setGet(int Get) {
            this.Get = Get;
        }

        public int getLost() {
            return Lost;
        }

        public void setLost(int Lost) {
            this.Lost = Lost;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getIdentity() {
            return Identity;
        }

        public void setIdentity(String Identity) {
            this.Identity = Identity;
        }

        public boolean isIsPartyMember() {
            return IsPartyMember;
        }

        public void setIsPartyMember(boolean IsPartyMember) {
            this.IsPartyMember = IsPartyMember;
        }

        public Object getJoinPartyDate() {
            return JoinPartyDate;
        }

        public void setJoinPartyDate(Object JoinPartyDate) {
            this.JoinPartyDate = JoinPartyDate;
        }

        public Object getLon() {
            return Lon;
        }

        public void setLon(Object Lon) {
            this.Lon = Lon;
        }

        public Object getLat() {
            return Lat;
        }

        public void setLat(Object Lat) {
            this.Lat = Lat;
        }

        public Object getSex() {
            return Sex;
        }

        public void setSex(Object Sex) {
            this.Sex = Sex;
        }

        public String getAreaCode() {
            return AreaCode;
        }

        public void setAreaCode(String AreaCode) {
            this.AreaCode = AreaCode;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public List<GetDetailBean> getGetDetail() {
            return GetDetail;
        }

        public void setGetDetail(List<GetDetailBean> GetDetail) {
            this.GetDetail = GetDetail;
        }

        public List<GetDetailBean> getLostDetail() {
            return LostDetail;
        }

        public void setLostDetail(List<GetDetailBean> LostDetail) {
            this.LostDetail = LostDetail;
        }

        public static class GetDetailBean {
            /**
             * Name : 积极参加社区文体活动
             * Type : 2
             * CreateDate : 2018-08-01
             * Comment : null
             * Score : 2
             */

            private String Name;
            private int Type;
            private String CreateDate;
            private Object Comment;
            private int Score;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public Object getComment() {
                return Comment;
            }

            public void setComment(Object Comment) {
                this.Comment = Comment;
            }

            public int getScore() {
                return Score;
            }

            public void setScore(int Score) {
                this.Score = Score;
            }
        }
}
