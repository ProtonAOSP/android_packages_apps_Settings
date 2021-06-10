/*
 * Copyright (C) 2021 The Proton AOSP Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.display;

import android.content.Context;
import android.os.SystemProperties;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

public class TouchSensitivityPreferenceController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    private static final String TOUCH_SENS_KEY = "touch_sens";
    static final String TOUCH_SENS_PROPERTY = "persist.vendor.touch_sensitivity_mode";

    public TouchSensitivityPreferenceController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return mContext.getResources().getBoolean(com.android.internal.R.bool.config_supportGloveMode);
    }

    @Override
    public String getPreferenceKey() {
        return TOUCH_SENS_KEY;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final boolean isEnabled = (Boolean) newValue;
        SystemProperties.set(TOUCH_SENS_PROPERTY, isEnabled ? "1" : "0");
        return true;
    }

    @Override
    public void updateState(Preference preference) {
        final boolean isEnabled = SystemProperties.getBoolean(TOUCH_SENS_PROPERTY, false);
        ((SwitchPreference) preference).setChecked(isEnabled);
    }
}
