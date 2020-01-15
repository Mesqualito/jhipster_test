import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormArray, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { Chooser, IChooser } from 'app/shared/model/chooser.model';
import { ChooserService } from './chooser.service';

@Component({
  selector: 'jhi-chooser-update',
  templateUrl: './chooser-update.component.html'
})
export class ChooserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    enumeration: []
  });

  constructor(protected chooserService: ChooserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chooser }) => {
      this.updateForm(chooser);
    });
  }

  updateForm(chooser: IChooser): void {
    this.editForm.patchValue({
      id: chooser.id,
      enumeration: chooser.enumeration
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chooser = this.createFromForm();
    if (chooser.id !== undefined) {
      this.subscribeToSaveResponse(this.chooserService.update(chooser));
    } else {
      this.subscribeToSaveResponse(this.chooserService.create(chooser));
    }
  }

  private createFromForm(): IChooser {
    return {
      ...new Chooser(),
      id: this.editForm.get(['id'])!.value,
      enumeration: this.editForm.get(['enumeration'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChooser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
