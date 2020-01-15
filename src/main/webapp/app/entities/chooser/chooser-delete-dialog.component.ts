import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChooser } from 'app/shared/model/chooser.model';
import { ChooserService } from './chooser.service';

@Component({
  templateUrl: './chooser-delete-dialog.component.html'
})
export class ChooserDeleteDialogComponent {
  chooser?: IChooser;

  constructor(protected chooserService: ChooserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chooserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('chooserListModification');
      this.activeModal.close();
    });
  }
}
