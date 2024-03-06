import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PublisherFormService, PublisherFormGroup } from './publisher-form.service';
import { IPublisher } from '../publisher.model';
import { PublisherService } from '../service/publisher.service';

@Component({
  selector: 'jhi-publisher-update',
  templateUrl: './publisher-update.component.html',
})
export class PublisherUpdateComponent implements OnInit {
  isSaving = false;
  publisher: IPublisher | null = null;

  editForm: PublisherFormGroup = this.publisherFormService.createPublisherFormGroup();

  constructor(
    protected publisherService: PublisherService,
    protected publisherFormService: PublisherFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publisher }) => {
      this.publisher = publisher;
      if (publisher) {
        this.updateForm(publisher);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const publisher = this.publisherFormService.getPublisher(this.editForm);
    if (publisher.id !== null) {
      this.subscribeToSaveResponse(this.publisherService.update(publisher));
    } else {
      this.subscribeToSaveResponse(this.publisherService.create(publisher));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublisher>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(publisher: IPublisher): void {
    this.publisher = publisher;
    this.publisherFormService.resetForm(this.editForm, publisher);
  }
}
