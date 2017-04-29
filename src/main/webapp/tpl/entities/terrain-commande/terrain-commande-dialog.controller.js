(function () {
    'use strict';

    angular
            .module('app')
            .controller('TerrainCommandeDialogController', TerrainCommandeDialogController);

    TerrainCommandeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'TerrainCommande', 'Fournisseur', 'Terrain', 'TerrainSuivi', 'Client','reglements'];

    function TerrainCommandeDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, TerrainCommande, Fournisseur, Terrain, TerrainSuivi, Client,reglements) {
        var vm = this;

        vm.terrainCommande = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.fournisseurs = Fournisseur.query();
        vm.clients = Client.query();
        vm.terrains = Terrain.query();
        vm.suividossiers = TerrainSuivi.query();
        vm.reglements=reglements;
   



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.terrainCommande.id !== null) {
                TerrainCommande.update(vm.terrainCommande, onSaveSuccess, onSaveError);
            } else {
                TerrainCommande.save(vm.terrainCommande, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:terrainCommandeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


        vm.datePickerOpenStatus.dateEmission = false;


        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.setMimage = function ($file, fieldName) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        vm.terrainCommande[fieldName] = base64Data;
                        vm.terrainCommande[fieldName + 'ContentType'] = $file.type;
                    });
                });
            }
        };

        vm.zoomColumn = function (lookupCtrl, lookupTemplate, fieldname, entity) {
            $uibModal.open({
                templateUrl: 'tpl/entities/' + lookupTemplate + '/' + lookupTemplate + '-dialog.html',
                controller: lookupCtrl + 'DialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function (item) {
                vm.terrainCommande[fieldname] = item;
            }, function () {

            });
        };

        vm.addReglement = function () {
            $uibModal.open({
                templateUrl: 'tpl/entities/terrain-reglement/terrain-reglement-dialog.html',
                controller: 'TerrainReglementDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            commande: vm.terrainCommande,
                            mode: 'ESPECES',
                            dateVersement: new Date()
                        };
                    }
                }
            }).result.then(function (item) {
                vm.reglements.push(item);
                vm.terrainCommande = TerrainCommande.get({id: $stateParams.id});
            }, function () {

            });
        };

    }
})();
