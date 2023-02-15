package com.example.project5.Common;

import org.springframework.stereotype.Service;

@Service
public class ScriptManager {

    private static ScriptManager scm_ins= new ScriptManager(); // singleton

    private ScriptManager(){

    }
    public static ScriptManager getInstance(){

        return scm_ins;
    }



    public String getSc_game_start() {
        return sc_game_start;
    }

    public void setSc_game_start(String sc_game_start) {
        this.sc_game_start = sc_game_start;
    }

    public String getSc_check_job() {
        return sc_check_job;
    }

    public void setSc_check_job(String sc_check_job) {
        this.sc_check_job = sc_check_job;
    }

    public String getSc_leader_first_begin() {
        return sc_leader_first_begin;
    }

    public void setSc_leader_first_begin(String sc_leader_first_begin) {
        this.sc_leader_first_begin = sc_leader_first_begin;
    }

    public String getSc_leader_begin() {
        return sc_leader_begin;
    }

    public void setSc_leader_begin(String sc_leader_begin) {
        this.sc_leader_begin = sc_leader_begin;
    }

    public String getSc_select_juror() {
        return sc_select_juror;
    }

    public void setSc_select_juror(String sc_select_juror) {
        this.sc_select_juror = sc_select_juror;
    }

    public String getSc_vote1_start() {
        return sc_vote1_start;
    }

    public void setSc_vote1_start(String sc_vote1_start) {
        this.sc_vote1_start = sc_vote1_start;
    }

    public String getSc_vote1_result() {
        return sc_vote1_result;
    }

    public void setSc_vote1_result(String sc_vote1_result) {
        this.sc_vote1_result = sc_vote1_result;
    }

    public String getSc_vote2_start() {
        return sc_vote2_start;
    }

    public void setSc_vote2_start(String sc_vote2_start) {
        this.sc_vote2_start = sc_vote2_start;
    }

    public String getSc_vote1_reset() {
        return sc_vote1_reset;
    }

    public void setSc_vote1_reset(String sc_vote1_reset) {
        this.sc_vote1_reset = sc_vote1_reset;
    }

    public String getSc_vote2_select() {
        return sc_vote2_select;
    }

    public void setSc_vote2_select(String sc_vote2_select) {
        this.sc_vote2_select = sc_vote2_select;
    }

    public String getSc_vote2_result() {
        return sc_vote2_result;
    }

    public void setSc_vote2_result(String sc_vote2_result) {
        this.sc_vote2_result = sc_vote2_result;
    }

    public String getSc_evil_normal_win() {
        return sc_evil_normal_win;
    }

    public void setSc_evil_normal_win(String sc_evil_normal_win) {
        this.sc_evil_normal_win = sc_evil_normal_win;
    }

    public String getSc_citizen_win() {
        return sc_citizen_win;
    }

    public void setSc_citizen_win(String sc_citizen_win) {
        this.sc_citizen_win = sc_citizen_win;
    }

    public String getSc_killer_start() {
        return sc_killer_start;
    }

    public void setSc_killer_start(String sc_killer_start) {
        this.sc_killer_start = sc_killer_start;
    }

    public String getSc_killer_select() {
        return sc_killer_select;
    }

    public void setSc_killer_select(String sc_killer_select) {
        this.sc_killer_select = sc_killer_select;
    }

    public String getSc_killer_success() {
        return sc_killer_success;
    }

    public void setSc_killer_success(String sc_killer_success) {
        this.sc_killer_success = sc_killer_success;
    }

    public String getSc_killer_fail() {
        return sc_killer_fail;
    }

    public void setSc_killer_fail(String sc_killer_fail) {
        this.sc_killer_fail = sc_killer_fail;
    }

    public String getSc_game_end() {
        return sc_game_end;
    }

    public void setSc_game_end(String sc_game_end) {
        this.sc_game_end = sc_game_end;
    }

    String sc_game_start = "Game Started";
    String sc_check_job= "당신의 직업을 확인하세요.";
    String sc_leader_first_begin = "배심원장이 랜덤으로 결정됩니다.";
    String sc_leader_begin="배심원장이 다음 시계방향으로 계승됩니다.";
    String sc_select_juror="배심원장은 배심원단의 멤버를 선택하세요.";
    String sc_vote1_start="배심원단 찬반 투표 개시";
    String sc_vote1_result="배심원단 찬반 투표 결과";
    String sc_vote2_start="배심원단이 재판에 참여합니다.";
    String sc_vote1_reset= "배심원단 구성 실패";
    String sc_vote2_select="배심원단은 이 재판의 유무죄를 신중히 선택해 결정해주세요.";
    String sc_vote2_result= "재판에 대한 배심원단의 결정을 공개하겠습니다.";
    String sc_evil_normal_win= "악의 세력이 승리하였습니다";
    String sc_citizen_win= "시민 세력이 승리하였습니다";
    String sc_killer_start= "암살자는 재판결과를 뒤엎기 위해 경찰을 찾아야 합니다...";
    String sc_killer_select= "경찰을 골라주세요.";
    String sc_killer_success= "경찰 암살 성공!";
    String sc_killer_fail= "경찰 암살 실패.";
    String sc_game_end= "game over";




}
