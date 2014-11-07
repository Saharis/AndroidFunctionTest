// ICountSerAIDL.aidl
package com.virgil.aft;
import com.virgil.aft.business.view.bean.CountBean;
// Declare any non-default types here with import statements

interface ICountSerAIDL {
            CountBean getCon();
            void setCon(in CountBean ob);
}
