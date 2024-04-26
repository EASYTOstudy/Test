public interface CallerInter {
    void call(Param param);
}
public interface ObserverInter{
    /**
     * 增加观察者
     *
     * @param callInter
     */
    void addObserver(CallerInter callInter);
 
    /**
     * 删除观察者
     *
     * @param lemmaWriteBackOpObserver
     */
    void delObserver(CallerInter callInter);
 
    /**
     * 通知观察者
     *
     * @param lemmaRecord
     */
    void notifyAllObserver(Param param);
}
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
 
/**
 * Created by denglinjie on 2016/8/19.
 */
@Service
public class ObserverImpl implements ObserverInter, InitializingBean {
 
    //要通知的对象列表
    private List<CallerInter> callerInterList;
 
    public void addObserver(CallerInter callInter) {
        callerInterList.add(callInter);
    }
 
    public void delObserver(CallerInter callInter) {
        callerInterList.remove(callInter);
    }
 
    public void notifyAllObserver(Param param) {
        for(CallerInter callerInter : callerInterList) {
            callerInter.call(param);
        }
    }
 
    public void afterPropertiesSet() throws Exception {
        callerInterList = new CopyOnWriteArrayList<CallerInter>();  //这里用一个线程安全的list，因为可能多线程同时操作它
    }
 
    /**
     * 对外提供一个接口，当这个接口被调用的时候，可以通知所有注册进来的对象的特定call接口
     */
    public void operateService() {
        //构造参数
        Param param = new Param();
        notifyAllObserver(param);
    }
}
import com.sogou.study.observer.CallerInter;
import com.sogou.study.observer.ObserverInter;
import com.sogou.study.observer.Param;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
 
/**
 * Created by denglinjie on 2016/8/19.
 */
public class CallImplFirst implements CallerInter, InitializingBean {
 
    @Autowired
    private ObserverInter observerInter;
 
    public void call(Param param) {
        //do something
    }
 
    public void afterPropertiesSet() throws Exception {
        observerInter.addObserver(this);
    }
}
import com.sogou.study.observer.CallerInter;
import com.sogou.study.observer.ObserverInter;
import com.sogou.study.observer.Param;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
 
/**
 * Created by denglinjie on 2016/8/19.
 */
public class CallImplSecond implements CallerInter, InitializingBean {
 
    @Autowired
    private ObserverInter observerInter;
 
    public void call(Param param) {
        //do something
    }
 
    public void afterPropertiesSet() throws Exception {
        observerInter.addObserver(this);
    }
}
