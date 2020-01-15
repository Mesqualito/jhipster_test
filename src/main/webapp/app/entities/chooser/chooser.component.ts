import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChooser } from 'app/shared/model/chooser.model';
import { ChooserService } from './chooser.service';
import { ChooserDeleteDialogComponent } from './chooser-delete-dialog.component';

@Component({
  selector: 'jhi-chooser',
  templateUrl: './chooser.component.html'
})
export class ChooserComponent implements OnInit, OnDestroy {
  choosers?: IChooser[];
  eventSubscriber?: Subscription;

  constructor(protected chooserService: ChooserService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.chooserService.query().subscribe((res: HttpResponse<IChooser[]>) => {
      this.choosers = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChoosers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChooser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChoosers(): void {
    this.eventSubscriber = this.eventManager.subscribe('chooserListModification', () => this.loadAll());
  }

  delete(chooser: IChooser): void {
    const modalRef = this.modalService.open(ChooserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chooser = chooser;
  }
}
