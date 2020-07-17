package com.world.play.ui.feature;

import com.world.play.ui.feature.base.Contract;

public abstract class ParentContract
{
    public interface Presenter extends Contract.Presenter<View> {

    }

    public interface View extends Contract.View {

    }
}
