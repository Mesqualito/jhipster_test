import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'chooser',
        loadChildren: () => import('./chooser/chooser.module').then(m => m.RadiobuttonsChooserModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class RadiobuttonsEntityModule {}
