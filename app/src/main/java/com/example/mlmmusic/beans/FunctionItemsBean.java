package com.example.mlmmusic.beans;

import com.example.mlmmusic.base.BaseBean;

public class FunctionItemsBean extends BaseBean {
        /**
         * name : 充值中心
         * isSelect : false
         * imageUrl : icon_home_selected
         * background : #86c751
         */

        private String name;
        private boolean isSelect;
        private String imageUrl;
        private String background;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isIsSelect() {
            return isSelect;
        }

        public void setIsSelect(boolean isSelect) {
            this.isSelect = isSelect;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }
}
