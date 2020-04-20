package com.atoncorp.trustin.sample.fingerprint;

import java.util.HashMap;
import java.util.Map;

import static androidx.biometric.BiometricConstants.ERROR_CANCELED;
import static androidx.biometric.BiometricConstants.ERROR_HW_NOT_PRESENT;
import static androidx.biometric.BiometricConstants.ERROR_HW_UNAVAILABLE;
import static androidx.biometric.BiometricConstants.ERROR_LOCKOUT;
import static androidx.biometric.BiometricConstants.ERROR_LOCKOUT_PERMANENT;
import static androidx.biometric.BiometricConstants.ERROR_NEGATIVE_BUTTON;
import static androidx.biometric.BiometricConstants.ERROR_NO_BIOMETRICS;
import static androidx.biometric.BiometricConstants.ERROR_NO_DEVICE_CREDENTIAL;
import static androidx.biometric.BiometricConstants.ERROR_NO_SPACE;
import static androidx.biometric.BiometricConstants.ERROR_TIMEOUT;
import static androidx.biometric.BiometricConstants.ERROR_UNABLE_TO_PROCESS;
import static androidx.biometric.BiometricConstants.ERROR_USER_CANCELED;
import static androidx.biometric.BiometricConstants.ERROR_VENDOR;

public enum BiometricError {
    COMMON_NULL(0, "error code not defined", "확인되지 않은 오류메시지입니다.", "androidx.biometric.BiometricConstants에 등록되지 않은 오류 코드 상태 메시지입니다."),
    BIOMETRIC_ERROR_HW_UNAVAILABLE(ERROR_HW_UNAVAILABLE, "하드웨어 사용불가 (HW unavailable)", "현재 단말 생체인증 HW를 사용할 수 없습니다. 잠시 후 다시 시도해 주세요.", "The hardware is unavailable. Try again later."),
    BIOMETRIC_ERROR_UNABLE_TO_PROCESS(ERROR_UNABLE_TO_PROCESS, "현재 정보 처리불가 (Unable to process current data)", "현재 현재 단말 HW에서 입력하신 생체 정보의 처리가 불가능 합니다.", "Error state returned when the sensor was unable to process the current image."),
    BIOMETRIC_ERROR_TIMEOUT(ERROR_TIMEOUT, "처리 시간초과 (Process timeout)", "현재 생체 인증 처리가 너무 오래 걸려 타임 아웃되었습니다.", "Error state returned when the current request has been running too long."),
    BIOMETRIC_ERROR_NO_SPACE(ERROR_NO_SPACE, "처리 공간 부족 (Not enough device storage left)", "단말 저장 공간이 부족하여 생체 인증 처리에 실패하였습니다.", "Error state returned for operations like enrollment; the operation cannot be completed because there's not enough storage remaining to complete the operation."),
    BIOMETRIC_ERROR_CANCELED(ERROR_CANCELED, "하드웨어 문제로 취소 (Cancelled due to HW issue)", "단말 생체인증 HW의 사용이 현재 불가능하여 처리가 취소되었습니다.", "The operation was canceled because the biometric sensor is unavailable."),
    BIOMETRIC_ERROR_LOCKOUT(ERROR_LOCKOUT, "시도 횟수 초과로 취소 (Cancelled due to too many attempts)", "시도횟수가 초과되어 인증처리가 취소되었습니다.", "The operation was canceled because the API is locked out due to too many attempts."),
    BIOMETRIC_ERROR_VENDOR(ERROR_VENDOR, "제조사 관련 문제발생 (HW vendor issue)", "단말 제조사 문제로 생체인증에 오류가 발생하였습니다.", "Hardware vendors may extend this list if there are conditions that do not fall under one of the above categories."),
    BIOMETRIC_ERROR_LOCKOUT_PERMANENT(ERROR_LOCKOUT_PERMANENT, "시도 횟수 초과로 인증불가 발생 (Cancelled by lockout)", "시도횟수가 초과되어 인증기능이 잠겨 처리가 취소되었습니다.", "The operation was canceled because ERROR_LOCKOUT occurred too many times."),
    BIOMETRIC_ERROR_USER_CANCELED(ERROR_USER_CANCELED, "사용자 취소 (User cancelled)", "사용자가 생체인증을 취소하였습니다.", "The user canceled the operation."),
    BIOMETRIC_ERROR_NO_BIOMETRICS(ERROR_NO_BIOMETRICS, "등록된 생체정보 없음 (No biometrics enrolled)", "현재 사용자의 등록된 생처정보가 없습니다.", "The user does not have any biometrics enrolled."),
    BIOMETRIC_ERROR_HW_NOT_PRESENT(ERROR_HW_NOT_PRESENT, "생체인증 관련 HW 없음 (Biometric HW not present)", "현재 단말에 생체인증 관련 HW가 없습니다.", "The device does not have a biometric sensor."),
    BIOMETRIC_ERROR_NEGATIVE_BUTTON(ERROR_NEGATIVE_BUTTON, "사용자 취소 (User clicks cancel button)", "사용자가 생체인증을 취소하였습니다.", "The user pressed the negative button."),
    BIOMETRIC_ERROR_NO_DEVICE_CREDENTIAL(ERROR_NO_DEVICE_CREDENTIAL, "단말 설정 PIN, 패턴, 암호 없음", "단말에 설정된 PIN, 잠김 패턴, 암호가 없습니다.", "The device does not have pin, pattern, or password set up.");

    private final int code;                 //에러 분류 코드 (androidx.biometric.BiometricConstants에 정의된 그대로 + 미 정의 예외 상황(0))
    private final String comment;           //에러 간단 설명
    private final String msg;               //에러 표시 메시지
    private final String msgForDeveloper;   //에러 메시지 개발자용 상세

    BiometricError(int code, String comment, String msg, String msgForDeveloper) {
        this.code = code;
        this.comment = comment;
        this.msg = msg;
        this.msgForDeveloper = msgForDeveloper;
    }

    public int getCode() {
        return this.code;
    }

    public String getComment() {
        return this.comment;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getMsgForDeveloper() {
        return this.msgForDeveloper;
    }

    private static final Map<String, BiometricError> findMap = new HashMap();

    static {
        for (BiometricError codes : values()) {
            findMap.put(String.valueOf(codes.getCode()), codes);
        }
    }

    /**
     * getByCode(int)
     *
     * @param codeInt Int type code param
     * @return
     */
    public static BiometricError getByCode(int codeInt) {
        return getByCode(String.valueOf(codeInt));
    }

    /**
     * getByCode(String)
     *
     * @param codeString String type code param
     * @return
     */
    public static BiometricError getByCode(String codeString) {
        BiometricError fmap = findMap.get(codeString);
        if (fmap == null) {
            return BiometricError.COMMON_NULL;
        } else {
            return fmap;
        }
    }

    public static String getBiometricErrorMessage(int errorCode, String errString) {
        String message = "";
        switch (errorCode) {
            case ERROR_HW_UNAVAILABLE://1
//                message = "생체 인식 하드웨어를 현재 사용할 수 없습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_UNABLE_TO_PROCESS://2
//                message = "생체 인식 과정을 진행 할 수 없습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_TIMEOUT://3
//                message = "인증 시도 시간이 초과되었습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_NO_SPACE://4
//                message = "단말 저장공간이 부족하여 진행할 수 없습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_CANCELED://5
                //message = "생체 인식 하드웨어를 사용할 수 없어 취소 되었습니다.";
//                message = "생체 인식 하드웨어를 사용할 수 없거나, 단말에 등록되지 않은 생체정보입니다.(E504)";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_LOCKOUT://7
//                message = "인증 시도 횟수(5회)가 초과되어 생체 인식 기능이 잠겼습니다. 30초 이상 지난 후 다시 시도하세요.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_VENDOR://8
//                message = "단말 제조사 관련 문제가 발생하였습니다. 제조사에 문의하세요.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_LOCKOUT_PERMANENT://9
//                message = "생체 인식 기능 잠김이 너무 많이 발생하여 영구 잠김 상태가 되었습니다. 시스템의 안내에 따라 설정하신 PIN, 패턴 혹은 비밀번호로 잠김을 해제하시고 다시 시도하세요.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_USER_CANCELED://10
//                message = "생체 인식을 취소하였습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_NO_BIOMETRICS://11
//                message = "등록된 생체정보가 없습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_HW_NOT_PRESENT://12
//                message = "생체 인식 하드웨어가 없습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_NEGATIVE_BUTTON://13
//                message = "취소 버튼으로 취소되었습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            case ERROR_NO_DEVICE_CREDENTIAL://14
//                message = "단말에 설정된 PIN, 잠김 패턴, 혹은 비밀번호가 없습니다. 시스템의 설정 메뉴에서 PIN, 패턴 혹은 비밀번호를 설정하시고 다시 시도하세요.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
            default:
//                message = "알 수 없는 이유로 동작할 수 없습니다.";
                message += "\nerrorCode: " + errorCode + ", message : " + errString;
                break;
        }
        return message;
    }
}
