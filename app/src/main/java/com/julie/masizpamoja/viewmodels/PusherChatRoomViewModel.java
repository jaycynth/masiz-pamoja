package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.julie.masizpamoja.datastates.GetAllChatsState;
import com.julie.masizpamoja.datastates.SentMessageState;
import com.julie.masizpamoja.repos.GetAllChatsRepo;
import com.julie.masizpamoja.repos.SentMessageRepo;

public class PusherChatRoomViewModel extends AndroidViewModel {

    private MediatorLiveData<GetAllChatsState> getAllChatsStateMediatorLiveData;
    private GetAllChatsRepo getAllChatsRepo;

    private MediatorLiveData<SentMessageState> sentMessageStateMediatorLiveData;
    private SentMessageRepo sentMessageRepo;

    public PusherChatRoomViewModel(@NonNull Application application) {
        super(application);

        getAllChatsStateMediatorLiveData = new MediatorLiveData<>();
        getAllChatsRepo = new GetAllChatsRepo(application);

        sentMessageStateMediatorLiveData = new MediatorLiveData<>();
        sentMessageRepo = new SentMessageRepo(application);
    }

    public LiveData<SentMessageState> getPostMessageResponse() {
        return sentMessageStateMediatorLiveData;
    }

    public void postMessage(String message, String accessToken) {
        final LiveData<SentMessageState> sentMessageStateLiveData = sentMessageRepo.postMessage(message, accessToken);

        sentMessageStateMediatorLiveData.addSource(sentMessageStateLiveData,
                new Observer<SentMessageState>() {
                    @Override
                    public void onChanged(SentMessageState sentMessageStateMediatorLiveData) {
                        if (PusherChatRoomViewModel.this.sentMessageStateMediatorLiveData.hasActiveObservers()) {
                            PusherChatRoomViewModel.this.sentMessageStateMediatorLiveData.removeSource(sentMessageStateLiveData);
                        }
                        PusherChatRoomViewModel.this.sentMessageStateMediatorLiveData.setValue(sentMessageStateMediatorLiveData);
                    }
                });
    }

    public LiveData<GetAllChatsState>  getAllChatsResponse(){
        return getAllChatsStateMediatorLiveData;
    }

    public void getAllChats(String accessToken){
        LiveData<GetAllChatsState> getAllChatsStateLiveData = getAllChatsRepo.getAllChats(accessToken);
        getAllChatsStateMediatorLiveData.addSource(getAllChatsStateLiveData, getAllChatsStateMediatorLiveData ->{
            if(getAllChatsStateLiveData.hasActiveObservers()){
                this.getAllChatsStateMediatorLiveData.removeSource(getAllChatsStateLiveData);
            }

            this.getAllChatsStateMediatorLiveData.setValue(getAllChatsStateMediatorLiveData);
        });
    }
}
