package com.demo.jetpack.ui.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by zp on 2019/12/3.
 */
public class LikeModel extends ViewModel {
    private MutableLiveData<Integer> aTeamScore;
    private MutableLiveData<Integer> bTeamScore;

    public MutableLiveData<Integer> getATeamScore() {
        if (aTeamScore == null) {
            aTeamScore = new MutableLiveData<>();
            aTeamScore.setValue(0);
        }
        return aTeamScore;
    }

    public MutableLiveData<Integer> getBTeamScore() {
        if (bTeamScore == null) {
            bTeamScore = new MutableLiveData<>();
            bTeamScore.setValue(0);
        }
        return bTeamScore;
    }

    public void addAScore(Integer a) {
        aTeamScore.setValue(aTeamScore.getValue() + a);
    }

    public void addBScore(Integer a) {
        bTeamScore.setValue(bTeamScore.getValue() + a);
    }

    public void reset(){
        aTeamScore.setValue(0);
        bTeamScore.setValue(0);
    }
}
