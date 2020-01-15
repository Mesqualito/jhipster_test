import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RadiobuttonsSharedModule } from 'app/shared/shared.module';
import { ChooserComponent } from './chooser.component';
import { ChooserDetailComponent } from './chooser-detail.component';
import { ChooserUpdateComponent } from './chooser-update.component';
import { ChooserDeleteDialogComponent } from './chooser-delete-dialog.component';
import { chooserRoute } from './chooser.route';

@NgModule({
  imports: [RadiobuttonsSharedModule, RouterModule.forChild(chooserRoute)],
  declarations: [ChooserComponent, ChooserDetailComponent, ChooserUpdateComponent, ChooserDeleteDialogComponent],
  entryComponents: [ChooserDeleteDialogComponent]
})
export class RadiobuttonsChooserModule {}
