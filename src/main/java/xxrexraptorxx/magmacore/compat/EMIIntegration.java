package xxrexraptorxx.magmacore.compat;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

@EmiEntrypoint
public class EMIIntegration implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        EMIIntegrationHelper.apply(registry);
    }

}