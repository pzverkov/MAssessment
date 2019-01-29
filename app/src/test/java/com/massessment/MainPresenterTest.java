package com.massessment;

import com.massessment.network.ConnectivityHelper;
import com.massessment.network.NetworkApi;
import com.massessment.network.data.Category;
import com.massessment.ui.main.MainPresenterImpl;
import com.massessment.ui.main.IMainScreen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import rx.ImmediateSchedulersRule;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MainPresenterTest {

    @Rule
    public final ImmediateSchedulersRule schedulers = new ImmediateSchedulersRule();
    @Mock
    NetworkApi networkApi;
    @Mock
    ConnectivityHelper helper;
    @Mock
    IMainScreen view;

    private MainPresenterImpl presenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenterImpl(networkApi, helper);
    }

    @Test
    public void thereIsDataOnAttach() {
        when(networkApi.getData()).thenReturn(Observable.just(Collections.singletonList(new Category())));

        presenter.onAttach(view);

        verify(view).setData(eq(0), anyListOf(Category.class));
    }

    @Test
    public void noDataOnAttach() {
        when(networkApi.getData()).thenReturn(Observable.error(IOException::new));

        presenter.onAttach(view);

        verify(view).showError(anyString());
    }
}
