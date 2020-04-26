package org.r.server.websocket.camera.handle.status;

import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.r.server.websocket.camera.enums.EventStatusCode;
import org.r.server.websocket.utils.XmlUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * date 20-4-10 下午4:47
 *
 * @author casper
 **/
@Component
@Slf4j
public class DetectEventHandleNode extends AbstractStatuesEventHandleNode<StatusEventContext> {




    @Override
    public boolean needHandle(int nStatusCode) {
        if (nStatusCode == EventStatusCode.EVENT_AI_FACEDETECT_IMAGEDATA_REPORT
                || nStatusCode == EventStatusCode.EVENT_AI_FACEDETECT_IMAGEDATA_REPORT_ONLYDATA
                || nStatusCode == EventStatusCode.EVENT_AI_FACERECOGNITION_REPORT
                || nStatusCode == EventStatusCode.EVENT_AI_FACEDETECT_REPORT
        ) {
            return true;
        }
        return false;
    }

    /*
    *
    * <RESPONSE_PARAM count="1" time="2020-04-14 06:00:30" dataid="2020-04-14 06:00:30_000_0209" datalen="340260" packcnt= "42" snapimage="3" filetype="1">
        <Face x="0" y="0" offsetX="512" offsetY="0" width="976" height="1076"></Face>
        <FaceRec UserId="586586103962" FaceId="586586103962" UserName="123456" Type="1" No="" Ps="" />
        <FaceExt Stranger="0" TrackId="209" Alive="84" Similar="9334" Result="1"/></RESPONSE_PARAM>
    *
    * */

    @Override
    public void handle0(StatusEventContext context, int errorCode, int actionCode) {

//        log.info("====================================get a call back=================================");
//        log.info(context.getPResponse());
        try {
            Document document = XmlUtil.parseXml(context.getPResponse());
            if (document == null) {
                return;
            }
            Element rootElement = document.getRootElement();
            if (rootElement == null) {
                return;
            }
            Element faceRec = rootElement.getChild("FaceRec");
            if (faceRec == null) {
                return;
            }

            String userId = faceRec.getAttributeValue("UserId");
//            log.info("get a face rec with userId: " + userId);
//            teachFaceMachineService.takeAttendance(context.getIUser(), Long.valueOf(userId));
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }


    }


}
