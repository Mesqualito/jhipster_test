import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChooser } from 'app/shared/model/chooser.model';

@Component({
  selector: 'jhi-chooser-detail',
  templateUrl: './chooser-detail.component.html'
})
export class ChooserDetailComponent implements OnInit {
  chooser: IChooser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chooser }) => {
      this.chooser = chooser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
