package com.example.mlmmusic.base;

import com.example.mlmmusic.beans.StreamsBean;

import java.util.List;

public class LiveListBean extends BaseBean {
    /**
     * liveChannel : [{"id":"1","name":"中国之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://www.radio.cn/img/default/2014/9/18/1411025486781.jpg","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn001.cnr.cn/live/zgzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn001.cnr.cn/live/zgzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn001.cnr.cn/live/zgzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=1","start":"15:00:00","end":"16:30:00","liveSectionName":"战疫情特别报道","commentId":"2004131","isCollect":"0","listen_num":"1.73亿"},{"id":"2","name":"经济之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://cnvod.cnr.cn/audio2017/ondemand/img/1100/20191227/1577439539975.png","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn002.cnr.cn/live/jjzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn002.cnr.cn/live/jjzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn002.cnr.cn/live/jjzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=2","start":"13:00:00","end":"16:00:00","liveSectionName":"交易实况下午版","commentId":"2004132","isCollect":"0","listen_num":"1.41亿"},{"id":"4","name":"音乐之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://cnvod.cnr.cn/audio2017/ondemand/img/1100/20191224/1577155745280.png","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn003.cnr.cn/live/yyzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn003.cnr.cn/live/yyzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn003.cnr.cn/live/yyzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=4","start":"14:00:00","end":"17:00:00","liveSectionName":"Music Corner","commentId":"2004134","isCollect":"0","listen_num":"3624万"},{"id":"5","name":"经典音乐广播","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://cnvod.cnr.cn/audio2017/ondemand/img/1100/20200108/1578449381052.png","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn004.cnr.cn/live/dszs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn004.cnr.cn/live/dszs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn004.cnr.cn/live/dszs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=5","start":"14:00:00","end":"16:00:00","liveSectionName":"民歌走天下","commentId":"2004135","isCollect":"0","listen_num":"1006.11万"},{"id":"3","name":"中华之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://www.radio.cn/img/default/2014/9/18/1411025614670.jpg","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn005.cnr.cn/live/zhzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn005.cnr.cn/live/zhzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn005.cnr.cn/live/zhzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=3","start":"14:00:00","end":"16:00:00","liveSectionName":"艺文两厅苑","commentId":"2004133","isCollect":"0","listen_num":"299.49万"},{"id":"6","name":"神州之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://www.radio.cn/img/default/2014/9/18/1411025635405.jpg","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn006.cnr.cn/live/szzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn006.cnr.cn/live/szzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn006.cnr.cn/live/szzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=6","start":"15:00:00","end":"16:00:00","liveSectionName":"涯爱转屋卡(客)","commentId":"2004136","isCollect":"0","listen_num":"215.21万"},{"id":"17","name":"大湾区之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://cnvod.cnr.cn/audio2017/ondemand/img/1100/20190830/1567159925260.png","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn007.cnr.cn/live/hxzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn007.cnr.cn/live/hxzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn007.cnr.cn/live/hxzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=17","start":"15:00:00","end":"16:00:00","liveSectionName":"风雅东方","commentId":"20041317","isCollect":"0","listen_num":"342.88万"},{"id":"7","name":"香港之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://www.radio.cn/img/default/2014/9/18/1411025662260.jpg","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn008.cnr.cn/live/xgzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn008.cnr.cn/live/xgzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn008.cnr.cn/live/xgzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=7","start":"15:00:00","end":"16:00:00","liveSectionName":"岭南音乐风","commentId":"2004137","isCollect":"0","listen_num":"230.19万"},{"id":"8","name":"民族之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://www.radio.cn/img/default/2014/9/18/1411026604061.jpg","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn009.cnr.cn/live/mzzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn009.cnr.cn/live/mzzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn009.cnr.cn/live/mzzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=8","start":"15:30:00","end":"16:00:00","liveSectionName":"深度热搜(朝)（重播）","commentId":"2004138","isCollect":"0","listen_num":"120.69万"},{"id":"9","name":"文艺之声","radio_id":"1","radio_name":"中央人民广播电台","channelPage":"1","img":"http://www.radio.cn/img/default/2014/9/18/1411025674178.jpg","streams":[{"bitstreamType":"","resolution":"H","url":"http://ngcdn010.cnr.cn/live/wyzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn010.cnr.cn/live/wyzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn010.cnr.cn/live/wyzs48/index.m3u8"}],"shareUrl":"http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=9","start":"15:00:00","end":"16:00:00","liveSectionName":"民歌风行","commentId":"2004139","isCollect":"0","listen_num":"765.68万"}]
     * status : 200
     * message : success
     */

    private int status;
    private String message;
    private List<LiveChannelBean> liveChannel;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LiveChannelBean> getLiveChannel() {
        return liveChannel;
    }

    public void setLiveChannel(List<LiveChannelBean> liveChannel) {
        this.liveChannel = liveChannel;
    }

    public static class LiveChannelBean {
    /**
     * id : 1
     * name : 中国之声
     * radio_id : 1
     * radio_name : 中央人民广播电台
     * channelPage : 1
     * img : http://www.radio.cn/img/default/2014/9/18/1411025486781.jpg
     * streams : [{"bitstreamType":"","resolution":"H","url":"http://ngcdn001.cnr.cn/live/zgzs/index.m3u8"},{"bitstreamType":"","resolution":"M","url":"http://ngcdn001.cnr.cn/live/zgzs48/index.m3u8"},{"bitstreamType":"","resolution":"L","url":"http://ngcdn001.cnr.cn/live/zgzs48/index.m3u8"}]
     * shareUrl : http://share.radio.cn/app_share_template/zhibo/zhibo.html?channelId=1
     * start : 15:00:00
     * end : 16:30:00
     * liveSectionName : 战疫情特别报道
     * commentId : 2004131
     * isCollect : 0
     * listen_num : 1.73亿
     */

    private String id;
    private String name;
    private String radio_id;
    private String radio_name;
    private String channelPage;
    private String img;
    private String shareUrl;
    private String start;
    private String end;
    private String liveSectionName;
    private String commentId;
    private String isCollect;
    private String listen_num;
    private List<StreamsBean> streams;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRadio_id() {
        return radio_id;
    }

    public void setRadio_id(String radio_id) {
        this.radio_id = radio_id;
    }

    public String getRadio_name() {
        return radio_name;
    }

    public void setRadio_name(String radio_name) {
        this.radio_name = radio_name;
    }

    public String getChannelPage() {
        return channelPage;
    }

    public void setChannelPage(String channelPage) {
        this.channelPage = channelPage;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLiveSectionName() {
        return liveSectionName;
    }

    public void setLiveSectionName(String liveSectionName) {
        this.liveSectionName = liveSectionName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getListen_num() {
        return listen_num;
    }

    public void setListen_num(String listen_num) {
        this.listen_num = listen_num;
    }

    public List<StreamsBean> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamsBean> streams) {
        this.streams = streams;
    }

        public static class StreamsBean {
            /**
             * bitstreamType :
             * resolution : H
             * url : http://ngcdn001.cnr.cn/live/zgzs/index.m3u8
             */

            private String bitstreamType;
            private String resolution;
            private String url;

            public String getBitstreamType() {
                return bitstreamType;
            }

            public void setBitstreamType(String bitstreamType) {
                this.bitstreamType = bitstreamType;
            }

            public String getResolution() {
                return resolution;
            }

            public void setResolution(String resolution) {
                this.resolution = resolution;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
