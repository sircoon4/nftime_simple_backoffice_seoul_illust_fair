package com.example.nftime_simple_backoffice_seoul_illust_fair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkInfoStore {

    private List<WorkInfo> workInfoList;

    public WorkInfoStore(){
        WorkInfo[] workInfos = {
            new WorkInfo(153, "Membership NFT (For Artist)", "nftime"),
            new WorkInfo(154, "Membership NFT (For Fan)", "nftime"),
            new WorkInfo(6, "BOOM", "5oo"),
            new WorkInfo(120, "death cannot", "Dusty Lyke"),
            new WorkInfo(87, "NOT OKAY", "SHINB"),
            new WorkInfo(94, "따뜻한 숨결 Heart-melting breath", "aitch8h"),
            new WorkInfo(128, "시그니쳐 단콩", "dankong"),
            new WorkInfo(98, "summer pory", "friends tory"),
            new WorkInfo(127, "사랑 해", "howow"),
            new WorkInfo(82, "cat hip", "그노"),
            new WorkInfo(121, "피렌체 두오모", "누리"),
            new WorkInfo(131, "City Night", "달봉"),
            new WorkInfo(125, "tomi", "달콤별"),
            new WorkInfo(96, "다정한 튤립", "도은"),
            new WorkInfo(126, "하늘바다 정류장", "듀박스"),
            new WorkInfo(133, "Morning", "드로린"),
            new WorkInfo(99, "LUCKY DAY !", "럭키포키"),
            new WorkInfo(97, "포옹.", "봄구름"),
            new WorkInfo(132, "계산적 대면", "선우현승"),
            new WorkInfo(116, "서울", "신영은"),
            new WorkInfo(123, "청춘", "유재이"),
            new WorkInfo(79, "찌라프의 여름", "찌드로잉"),
            new WorkInfo(130, "생일파티", "테일 투 테일"),
            new WorkInfo(134, "체리소다", "함조이"),
            new WorkInfo(124, "무지막지한 레굴루스", "해문"),
            new WorkInfo(118, "A new era is coming", "훈어"),
        };
        workInfoList = Arrays.asList(workInfos);
    }

    public List<WorkInfo> getWorkInfoList() {
        return workInfoList;
    }
}
