(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueFournisseurDialogController', CompteAnalytiqueFournisseurDialogController);

    CompteAnalytiqueFournisseurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'CompteAnalytiqueFournisseur','Fournisseur'];

    function CompteAnalytiqueFournisseurDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, CompteAnalytiqueFournisseur ,Fournisseur) {
        var vm = this;

        vm.compteAnalytiqueFournisseur = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.fournisseurs = Fournisseur.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.compteAnalytiqueFournisseur.id !== null) {
                CompteAnalytiqueFournisseur.update(vm.compteAnalytiqueFournisseur, onSaveSuccess, onSaveError);
            } else {
                CompteAnalytiqueFournisseur.save(vm.compteAnalytiqueFournisseur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:compteAnalytiqueFournisseurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
         vm.setMimage = function ($file, fieldName) {
                if ($file && $file.$error === 'pattern') {
                    return;
                }
                if ($file) {
                    DataUtils.toBase64($file, function (base64Data) {
                        $scope.$apply(function () {
                            vm.compteAnalytiqueFournisseur[fieldName] = base64Data;
                            vm.compteAnalytiqueFournisseur[fieldName + 'ContentType'] = $file.type;
                        });
                    });
                }
            };
            
            vm.zoomColumn = function (lookupCtrl,lookupTemplate, fieldname, entity) {
                $uibModal.open({
                    templateUrl: 'tpl/entities/'+lookupTemplate+'/'+lookupTemplate+'-dialog.html',
                    controller: lookupCtrl+'DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return entity;
                        }
                    }
                }).result.then(function(item) {
                        vm.compteAnalytiqueFournisseur[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
