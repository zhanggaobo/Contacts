package com.zhanggb.contacts.app.model;

import java.util.HashMap;

/**
 * @author zhanggaobo
 * @since 11/02/2016
 */
public class AttributionItem {

    private int id;
    private String number = "";
    private String province = "";
    private String city = "";
    private String areaCode = "";
    private String postcode = "";
    private String attribution = "";
    public static HashMap<String, String> areaAndCity = new HashMap<String, String>();

    static {
        areaAndCity.put("010", "北京");
        areaAndCity.put("020", "广东广州");
        areaAndCity.put("021", "上海");
        areaAndCity.put("022", "天津");
        areaAndCity.put("023", "重庆");
        areaAndCity.put("024", "辽宁沈阳");
        areaAndCity.put("025", "江苏南京");
        areaAndCity.put("027", "湖北武汉");
        areaAndCity.put("028", "四川眉山");
        areaAndCity.put("029", "陕西西安");
        areaAndCity.put("0310", "河北邯郸");
        areaAndCity.put("0311", "河北石家庄");
        areaAndCity.put("0312", "河北保定");
        areaAndCity.put("0313", "河北张家口");
        areaAndCity.put("0314", "河北承德");
        areaAndCity.put("0315", "河北唐山");
        areaAndCity.put("0316", "河北廊坊");
        areaAndCity.put("0317", "河北沧州");
        areaAndCity.put("0318", "河北衡水");
        areaAndCity.put("0319", "河北邢台");
        areaAndCity.put("0335", "河北秦皇岛");
        areaAndCity.put("0349", "山西朔州");
        areaAndCity.put("0350", "山西忻州");
        areaAndCity.put("0351", "山西太原");
        areaAndCity.put("0352", "山西大同");
        areaAndCity.put("0353", "山西阳泉");
        areaAndCity.put("0354", "山西晋中");
        areaAndCity.put("0355", "山西长治");
        areaAndCity.put("0356", "山西晋城");
        areaAndCity.put("0357", "山西临汾");
        areaAndCity.put("0358", "山西吕梁");
        areaAndCity.put("0359", "山西运城");
        areaAndCity.put("0370", "河南商丘");
        areaAndCity.put("0371", "河南郑州");
        areaAndCity.put("0372", "河南安阳");
        areaAndCity.put("0373", "河南新乡");
        areaAndCity.put("0374", "河南许昌");
        areaAndCity.put("0375", "河南平顶山");
        areaAndCity.put("0376", "河南信阳");
        areaAndCity.put("0377", "河南南阳");
        areaAndCity.put("0378", "河南开封");
        areaAndCity.put("0379", "河南洛阳");
        areaAndCity.put("0391", "河南焦作");
        areaAndCity.put("0392", "河南鹤壁");
        areaAndCity.put("0393", "河南濮阳");
        areaAndCity.put("0394", "河南周口");
        areaAndCity.put("0395", "河南漯河");
        areaAndCity.put("0396", "河南驻马店");
        areaAndCity.put("0398", "河南三门峡");
        areaAndCity.put("0410", "辽宁铁岭");
        areaAndCity.put("0411", "辽宁大连");
        areaAndCity.put("0412", "辽宁鞍山");
        areaAndCity.put("0413", "辽宁抚顺");
        areaAndCity.put("0414", "辽宁本溪");
        areaAndCity.put("0415", "辽宁丹东");
        areaAndCity.put("0416", "辽宁锦州");
        areaAndCity.put("0417", "辽宁营口");
        areaAndCity.put("0418", "辽宁阜新");
        areaAndCity.put("0419", "辽宁辽阳");
        areaAndCity.put("0421", "辽宁朝阳");
        areaAndCity.put("0427", "辽宁盘锦");
        areaAndCity.put("0429", "辽宁葫芦岛");
        areaAndCity.put("0431", "吉林长春");
        areaAndCity.put("0432", "吉林");
        areaAndCity.put("0434", "吉林四平");
        areaAndCity.put("0435", "吉林通化");
        areaAndCity.put("0436", "吉林白城");
        areaAndCity.put("0437", "吉林辽源");
        areaAndCity.put("0438", "吉林松原");
        areaAndCity.put("0439", "吉林白山");
        areaAndCity.put("0451", "黑龙江哈尔滨");
        areaAndCity.put("0452", "黑龙江齐齐哈尔");
        areaAndCity.put("0453", "黑龙江牡丹江");
        areaAndCity.put("0454", "黑龙江佳木斯");
        areaAndCity.put("0455", "黑龙江绥化");
        areaAndCity.put("0456", "黑龙江黑河");
        areaAndCity.put("0457", "黑龙江大兴安岭");
        areaAndCity.put("0458", "黑龙江伊春");
        areaAndCity.put("0459", "黑龙江大庆");
        areaAndCity.put("0464", "黑龙江七台河");
        areaAndCity.put("0467", "黑龙江鸡西");
        areaAndCity.put("0468", "黑龙江鹤岗");
        areaAndCity.put("0469", "黑龙江双鸭山");
        areaAndCity.put("0470", "内蒙古呼伦贝尔");
        areaAndCity.put("0471", "内蒙古呼和浩特");
        areaAndCity.put("0472", "内蒙古包头");
        areaAndCity.put("0473", "内蒙古乌海");
        areaAndCity.put("0474", "内蒙古乌兰察布");
        areaAndCity.put("0475", "内蒙古通辽");
        areaAndCity.put("0476", "内蒙古赤峰");
        areaAndCity.put("0477", "内蒙古鄂尔多斯");
        areaAndCity.put("0478", "内蒙古巴彦淖尔");
        areaAndCity.put("0479", "内蒙古锡林郭勒");
        areaAndCity.put("0482", "内蒙古兴安");
        areaAndCity.put("0483", "内蒙古阿拉善");
        areaAndCity.put("0510", "江苏无锡");
        areaAndCity.put("0511", "江苏镇江");
        areaAndCity.put("0512", "江苏苏州");
        areaAndCity.put("0513", "江苏南通");
        areaAndCity.put("0514", "江苏扬州");
        areaAndCity.put("0515", "江苏盐城");
        areaAndCity.put("0516", "江苏徐州");
        areaAndCity.put("0517", "江苏淮安");
        areaAndCity.put("0518", "江苏连云港");
        areaAndCity.put("0519", "江苏常州");
        areaAndCity.put("0523", "江苏泰州");
        areaAndCity.put("0527", "江苏宿迁");
        areaAndCity.put("0530", "山东菏泽");
        areaAndCity.put("0531", "山东济南");
        areaAndCity.put("0532", "山东青岛");
        areaAndCity.put("0533", "山东淄博");
        areaAndCity.put("0534", "山东德州");
        areaAndCity.put("0535", "山东烟台");
        areaAndCity.put("0536", "山东潍坊");
        areaAndCity.put("0537", "山东济宁");
        areaAndCity.put("0538", "山东泰安");
        areaAndCity.put("0539", "山东临沂");
        areaAndCity.put("0543", "山东滨州");
        areaAndCity.put("0546", "山东东营");
        areaAndCity.put("0550", "安徽滁州");
        areaAndCity.put("0551", "安徽合肥");
        areaAndCity.put("0552", "安徽蚌埠");
        areaAndCity.put("0553", "安徽芜湖");
        areaAndCity.put("0554", "安徽淮南");
        areaAndCity.put("0555", "安徽马鞍山");
        areaAndCity.put("0556", "安徽安庆");
        areaAndCity.put("0557", "安徽宿州");
        areaAndCity.put("0558", "安徽亳州");
        areaAndCity.put("0559", "安徽黄山");
        areaAndCity.put("0561", "安徽淮北");
        areaAndCity.put("0562", "安徽铜陵");
        areaAndCity.put("0563", "安徽宣城");
        areaAndCity.put("0564", "安徽六安");
        areaAndCity.put("0565", "安徽巢湖");
        areaAndCity.put("0566", "安徽池州");
        areaAndCity.put("0570", "浙江衢州");
        areaAndCity.put("0571", "浙江杭州");
        areaAndCity.put("0572", "浙江湖州");
        areaAndCity.put("0573", "浙江嘉兴");
        areaAndCity.put("0574", "浙江宁波");
        areaAndCity.put("0575", "浙江绍兴");
        areaAndCity.put("0576", "浙江台州");
        areaAndCity.put("0577", "浙江温州");
        areaAndCity.put("0578", "浙江丽水");
        areaAndCity.put("0579", "浙江金华");
        areaAndCity.put("0580", "浙江舟山");
        areaAndCity.put("0591", "福建福州");
        areaAndCity.put("0592", "福建厦门");
        areaAndCity.put("0593", "福建宁德");
        areaAndCity.put("0594", "福建莆田");
        areaAndCity.put("0595", "福建泉州");
        areaAndCity.put("0596", "福建漳州");
        areaAndCity.put("0597", "福建龙岩");
        areaAndCity.put("0598", "福建三明");
        areaAndCity.put("0599", "福建南平");
        areaAndCity.put("0631", "山东威海");
        areaAndCity.put("0632", "山东枣庄");
        areaAndCity.put("0633", "山东日照");
        areaAndCity.put("0634", "山东莱芜");
        areaAndCity.put("0635", "山东聊城");
        areaAndCity.put("0660", "广东汕尾");
        areaAndCity.put("0662", "广东阳江");
        areaAndCity.put("0663", "广东揭阳");
        areaAndCity.put("0668", "广东茂名");
        areaAndCity.put("0691", "云南西双版纳");
        areaAndCity.put("0692", "云南德宏");
        areaAndCity.put("0701", "江西鹰潭");
        areaAndCity.put("0710", "湖北襄樊");
        areaAndCity.put("0711", "湖北鄂州");
        areaAndCity.put("0712", "湖北孝感");
        areaAndCity.put("0713", "湖北黄冈");
        areaAndCity.put("0714", "湖北黄石");
        areaAndCity.put("0715", "湖北咸宁");
        areaAndCity.put("0716", "湖北荆州");
        areaAndCity.put("0717", "湖北宜昌");
        areaAndCity.put("0718", "湖北恩施");
        areaAndCity.put("0719", "湖北十堰");
        areaAndCity.put("0722", "湖北随州");
        areaAndCity.put("0724", "湖北荆门");
        areaAndCity.put("0728", "湖北江汉");
        areaAndCity.put("0730", "湖南岳阳");
        areaAndCity.put("0731", "湖南长沙");
        areaAndCity.put("0734", "湖南衡阳");
        areaAndCity.put("0735", "湖南郴州");
        areaAndCity.put("0736", "湖南常德");
        areaAndCity.put("0737", "湖南益阳");
        areaAndCity.put("0738", "湖南娄底");
        areaAndCity.put("0739", "湖南邵阳");
        areaAndCity.put("0743", "湖南湘西");
        areaAndCity.put("0744", "湖南张家界");
        areaAndCity.put("0745", "湖南怀化");
        areaAndCity.put("0746", "湖南永州");
        areaAndCity.put("0750", "广东江门");
        areaAndCity.put("0751", "广东韶关");
        areaAndCity.put("0752", "广东惠州");
        areaAndCity.put("0753", "广东梅州");
        areaAndCity.put("0754", "广东汕头");
        areaAndCity.put("0755", "广东深圳");
        areaAndCity.put("0756", "广东珠海");
        areaAndCity.put("0757", "广东佛山");
        areaAndCity.put("0758", "广东肇庆");
        areaAndCity.put("0759", "广东湛江");
        areaAndCity.put("0760", "广东中山");
        areaAndCity.put("0762", "广东河源");
        areaAndCity.put("0763", "广东清远");
        areaAndCity.put("0766", "广东云浮");
        areaAndCity.put("0768", "广东潮州");
        areaAndCity.put("0769", "广东东莞");
        areaAndCity.put("0770", "广西防城港");
        areaAndCity.put("0771", "广西南宁");
        areaAndCity.put("0772", "广西来宾");
        areaAndCity.put("0773", "广西桂林");
        areaAndCity.put("0774", "广西梧州");
        areaAndCity.put("0775", "广西贵港");
        areaAndCity.put("0776", "广西百色");
        areaAndCity.put("0777", "广西钦州");
        areaAndCity.put("0778", "广西河池");
        areaAndCity.put("0779", "广西北海");
        areaAndCity.put("0790", "江西新余");
        areaAndCity.put("0791", "江西南昌");
        areaAndCity.put("0792", "江西九江");
        areaAndCity.put("0793", "江西上饶");
        areaAndCity.put("0794", "江西抚州");
        areaAndCity.put("0795", "江西宜春");
        areaAndCity.put("0796", "江西吉安");
        areaAndCity.put("0797", "江西赣州");
        areaAndCity.put("0798", "江西景德镇");
        areaAndCity.put("0799", "江西萍乡");
        areaAndCity.put("0812", "四川攀枝花");
        areaAndCity.put("0813", "四川自贡");
        areaAndCity.put("0816", "四川绵阳");
        areaAndCity.put("0817", "四川南充");
        areaAndCity.put("0818", "四川达州");
        areaAndCity.put("0825", "四川遂宁");
        areaAndCity.put("0826", "四川广安");
        areaAndCity.put("0827", "四川巴中");
        areaAndCity.put("0830", "四川泸州");
        areaAndCity.put("0831", "四川宜宾");
        areaAndCity.put("0832", "四川内江");
        areaAndCity.put("0833", "四川乐山");
        areaAndCity.put("0834", "四川凉山");
        areaAndCity.put("0835", "四川雅安");
        areaAndCity.put("0836", "四川甘孜");
        areaAndCity.put("0837", "四川阿坝");
        areaAndCity.put("0838", "四川德阳");
        areaAndCity.put("0839", "四川广元");
        areaAndCity.put("0851", "贵州贵阳");
        areaAndCity.put("0852", "贵州遵义");
        areaAndCity.put("0853", "贵州安顺");
        areaAndCity.put("0854", "贵州黔南");
        areaAndCity.put("0855", "贵州黔东南");
        areaAndCity.put("0856", "贵州铜仁");
        areaAndCity.put("0857", "贵州毕节");
        areaAndCity.put("0858", "贵州六盘水");
        areaAndCity.put("0859", "贵州黔西南");
        areaAndCity.put("0870", "云南昭通");
        areaAndCity.put("0871", "云南昆明");
        areaAndCity.put("0872", "云南大理");
        areaAndCity.put("0873", "云南红河");
        areaAndCity.put("0874", "云南曲靖");
        areaAndCity.put("0875", "云南保山");
        areaAndCity.put("0876", "云南文山");
        areaAndCity.put("0877", "云南玉溪");
        areaAndCity.put("0878", "云南楚雄");
        areaAndCity.put("0879", "云南普洱");
        areaAndCity.put("0883", "云南临沧");
        areaAndCity.put("0886", "云南怒江");
        areaAndCity.put("0887", "云南迪庆");
        areaAndCity.put("0888", "云南丽江");
        areaAndCity.put("0891", "西藏拉萨");
        areaAndCity.put("0892", "西藏日喀则");
        areaAndCity.put("0893", "西藏山南");
        areaAndCity.put("0894", "西藏林芝");
        areaAndCity.put("0895", "西藏昌都");
        areaAndCity.put("0896", "西藏那曲");
        areaAndCity.put("0897", "西藏阿里");
        areaAndCity.put("0898", "海南海口");
        areaAndCity.put("0901", "新疆塔城");
        areaAndCity.put("0902", "新疆哈密");
        areaAndCity.put("0903", "新疆和田");
        areaAndCity.put("0906", "新疆阿勒泰");
        areaAndCity.put("0908", "新疆克孜");
        areaAndCity.put("0909", "新疆博尔塔拉");
        areaAndCity.put("0911", "陕西延安");
        areaAndCity.put("0912", "陕西榆林");
        areaAndCity.put("0913", "陕西渭南");
        areaAndCity.put("0914", "陕西商洛");
        areaAndCity.put("0915", "陕西安康");
        areaAndCity.put("0916", "陕西汉中");
        areaAndCity.put("0917", "陕西宝鸡");
        areaAndCity.put("0919", "陕西铜川");
        areaAndCity.put("0930", "甘肃临夏");
        areaAndCity.put("0931", "甘肃兰州");
        areaAndCity.put("0932", "甘肃定西");
        areaAndCity.put("0933", "甘肃平凉");
        areaAndCity.put("0934", "甘肃庆阳");
        areaAndCity.put("0935", "甘肃武威");
        areaAndCity.put("0936", "甘肃张掖");
        areaAndCity.put("0937", "甘肃酒泉");
        areaAndCity.put("0938", "甘肃天水");
        areaAndCity.put("0939", "甘肃陇南");
        areaAndCity.put("0941", "甘肃甘南");
        areaAndCity.put("0943", "甘肃白银");
        areaAndCity.put("0951", "宁夏银川");
        areaAndCity.put("0952", "宁夏石嘴山");
        areaAndCity.put("0953", "宁夏吴忠");
        areaAndCity.put("0954", "宁夏固原");
        areaAndCity.put("0955", "宁夏中卫");
        areaAndCity.put("0970", "青海海北");
        areaAndCity.put("0971", "青海西宁");
        areaAndCity.put("0972", "青海海东");
        areaAndCity.put("0973", "青海黄南");
        areaAndCity.put("0974", "青海海南");
        areaAndCity.put("0975", "青海果洛");
        areaAndCity.put("0976", "青海玉树");
        areaAndCity.put("0977", "青海海西");
        areaAndCity.put("0979", "青海格尔木");
        areaAndCity.put("0990", "新疆克拉玛依");
        areaAndCity.put("0991", "新疆乌鲁木齐");
        areaAndCity.put("0992", "新疆奎屯");
        areaAndCity.put("0993", "新疆石河子");
        areaAndCity.put("0994", "新疆昌吉");
        areaAndCity.put("0995", "新疆吐鲁番");
        areaAndCity.put("0996", "新疆巴音郭楞");
        areaAndCity.put("0997", "新疆阿克苏");
        areaAndCity.put("0998", "新疆喀什");
        areaAndCity.put("0999", "新疆伊犁");
    }


    public static String findCityByAreaCode(String areaCode) {
        return areaAndCity.get(areaCode);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }
}
