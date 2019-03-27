/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.common.app;

import bisq.common.CommonOptionKeys;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DevEnv {
    public static void setup(Injector injector) {
        DevEnv.setDevMode(injector.getInstance(Key.get(Boolean.class, Names.named(CommonOptionKeys.USE_DEV_MODE))));
    }

    // The UI got set the private dev key so the developer does not need to do anything and can test those features.
    // Features: Arbitration registration (alt+R at account), Alert/Update (alt+m), private message to a
    // peer (click user icon and alt+r), filter/block offers by various data like offer ID (cmd + f).
    // The user can set a program argument to ignore all of those privileged network_messages. They are intended for
    // emergency cases only (beside update message and arbitrator registration).
    public static final String DEV_PRIVILEGE_PUB_KEY = "027a381b5333a56e1cc3d90d3a7d07f26509adf7029ed06fc997c656621f8da1ee";
    public static final String DEV_PRIVILEGE_PRIV_KEY = "6ac43ea1df2a290c1c8391736aa42e4339c5cb4f110ff0257a13b63211977b7a";

    // If set to true we ignore several UI behavior like confirmation popups as well dummy accounts are created and
    // offers are filled with default values. Intended to make dev testing faster.
    @SuppressWarnings("PointlessBooleanExpression")
    private static boolean devMode = false;

    public static boolean isDevMode() {
        return devMode;
    }

    public static void setDevMode(boolean devMode) {
        DevEnv.devMode = devMode;
    }

    private static final boolean DAO_PHASE2_ACTIVATED = false;
    private static final boolean DAO_TRADING_ACTIVATED = false;

    private static boolean daoActivated = false;

    public static boolean isDaoActivated() {
        return daoActivated;
    }

    public static void setDaoActivated(boolean daoActivated) {
        DevEnv.daoActivated = daoActivated;
    }

    public static void logErrorAndThrowIfDevMode(String msg) {
        log.error(msg);
        if (devMode)
            throw new RuntimeException(msg);
    }

    public static boolean isDaoPhase2Activated() {
        return DAO_PHASE2_ACTIVATED || daoActivated;
    }

    public static boolean isDaoTradingActivated() {
        return DAO_TRADING_ACTIVATED || daoActivated;
    }
}